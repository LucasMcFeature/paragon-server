package com.kodemore.filter;

import com.kodemore.collection.KmList;
import com.kodemore.dao.KmDaoSessionManager;
import com.kodemore.time.KmDate;
import com.kodemore.time.KmTimestamp;
import com.kodemore.utility.KmClock;

/**
 * I provide a simple implementation of KmFilterIF.
 * Non-optimized subclasses need only implement the
 * findAll() method.
 */
public abstract class KmFilter<T>
    implements KmFilterIF<T>
{
    //##################################################
    //# find
    //##################################################

    @Override
    public abstract KmList<T> findAll();

    @Override
    public KmList<T> findBatch(int index, int count)
    {
        return findAll().getIndexCountSafe(index, count);
    }

    @Override
    public KmList<T> findFirst(int count)
    {
        return findBatch(0, count);
    }

    @Override
    public T findFirst()
    {
        return findFirst(1).getFirstSafe();
    }

    @Override
    public Iterable<T> getCursor()
    {
        return findAll();
    }

    //##################################################
    //# count
    //##################################################

    @Override
    public int getCount()
    {
        return findAll().size();
    }

    @Override
    public boolean exists()
    {
        return findFirst() != null;
    }

    //##################################################
    //# delete
    //##################################################

    @Override
    public void deleteAll()
    {
        KmList<T> v = findAll();
        for ( T e : v )
            delete(e);
    }

    @Override
    public void deleteFirst()
    {
        deleteFirst(1);
    }

    @Override
    public void deleteFirst(int count)
    {
        KmList<T> v = findFirst(count);
        for ( T e : v )
            delete(e);
    }

    protected void delete(T e)
    {
        KmDaoSessionManager.getInstance().getDaoSession().delete(e);
    }

    //##################################################
    //# dao
    //##################################################

    public KmFilterIF<T> toDaoFilter()
    {
        return new KmDaoFilter<T>(this);
    }

    //##################################################
    //# support
    //##################################################

    protected KmTimestamp getNowUtc()
    {
        return KmClock.getNowUtc();
    }

    protected KmDate getTodayUtc()
    {
        return KmClock.getTodayUtc();
    }

    @SuppressWarnings("unchecked")
    protected T cast(Object o)
    {
        return (T)o;
    }

    @SuppressWarnings("unchecked")
    protected KmList<T> castList(KmList<?> o)
    {
        return (KmList<T>)o;
    }

}
