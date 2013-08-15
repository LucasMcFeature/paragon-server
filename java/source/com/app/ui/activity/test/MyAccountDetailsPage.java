package com.app.ui.activity.test;

import com.kodemore.adaptor.KmAdaptorIF;
import com.kodemore.collection.KmList;
import com.kodemore.filter.KmFilterFactoryIF;
import com.kodemore.filter.KmFilterIF;
import com.kodemore.servlet.action.ScAction;
import com.kodemore.servlet.action.ScActionIF;
import com.kodemore.servlet.control.ScActionButton;
import com.kodemore.servlet.control.ScArray;
import com.kodemore.servlet.control.ScBox;
import com.kodemore.servlet.control.ScDialog;
import com.kodemore.servlet.control.ScDiv;
import com.kodemore.servlet.control.ScErrorBox;
import com.kodemore.servlet.control.ScFieldTable;
import com.kodemore.servlet.control.ScFilterBox;
import com.kodemore.servlet.control.ScForm;
import com.kodemore.servlet.control.ScFrame;
import com.kodemore.servlet.control.ScFrameChild;
import com.kodemore.servlet.control.ScGrid;
import com.kodemore.servlet.control.ScGridColumn;
import com.kodemore.servlet.control.ScGroup;
import com.kodemore.servlet.control.ScPageRoot;
import com.kodemore.servlet.control.ScText;
import com.kodemore.servlet.field.ScAutoCompleteCallbackIF;
import com.kodemore.servlet.field.ScAutoCompleteField;
import com.kodemore.servlet.field.ScDropdown;
import com.kodemore.servlet.field.ScField;
import com.kodemore.servlet.field.ScPasswordField;
import com.kodemore.servlet.field.ScTextField;
import com.kodemore.servlet.variable.ScLocalString;
import com.kodemore.utility.Kmu;

import com.app.dao.MyAccountDao;
import com.app.dao.MyUserDao;
import com.app.filter.MyAccountUserFilter;
import com.app.model.MyAccount;
import com.app.model.MyAccountType;
import com.app.model.MyAccountUser;
import com.app.model.MyUser;
import com.app.model.meta.MyMetaAccountUser;
import com.app.utility.MyButtonUrls;

public class MyAccountDetailsPage
    extends MyAbstractTestPage
{
    //##################################################
    //# singleton
    //##################################################

    public static final MyAccountDetailsPage instance = new MyAccountDetailsPage();

    private MyAccountDetailsPage()
    {
        // singleton
    }

    //##################################################
    //# variables
    //##################################################

    private ScDialog              _deleteDialog;

    private ScAutoCompleteField   _searchUserNameField;
    private ScAutoCompleteField   _addUserNameField;
    private ScAutoCompleteField   _searchAccountNameField;
    private ScAutoCompleteField   _addAccountNameField;

    private ScTextField           _editUserNameField;
    private ScTextField           _editAccountNameField;
    private ScTextField           _addUserEmailField;

    private ScDropdown            _searchTypeDropdown;
    private ScDropdown            _addTypeDropdown;
    private ScDropdown            _editTypeDropdown;
    private ScDropdown            _searchRoleDropdown;
    private ScDropdown            _addRoleDropdown;
    private ScDropdown            _editRoleDropdown;

    private ScFilterBox           _accountUserSearchBox;

    private ScGrid<MyAccountUser> _accountUserGrid;

    private ScFrame               _accountUserFrame;
    private ScFrame               _passwordFrame;

    private ScFrameChild          _viewAccountUserChild;
    private ScFrameChild          _addAccountUserChild;
    private ScFrameChild          _editAccountUserChild;
    private ScFrameChild          _passwordChild;

    private ScField<String>       _editUserEmailField;

    private ScText                _viewAccountName;
    private ScText                _viewType;
    private ScText                _viewRole;
    private ScText                _viewUserNameField;
    private ScText                _viewUserEmailField;
    private ScText                _viewUserVerifiedField;

    private ScBox                 _equalizeBox;

    private ScPasswordField       _password1Field;
    private ScPasswordField       _password2Field;

    private ScForm                _form;

    private ScLocalString         _userName;
    private ScLocalString         _userEmail;
    private ScLocalString         _accountName;

    //##################################################
    //# install
    //##################################################

    @Override
    protected ScPageRoot installRoot()
    {
        _userName = new ScLocalString();
        _userName.setAutoSave();

        _userEmail = new ScLocalString();
        _userEmail.setAutoSave();

        _accountName = new ScLocalString();
        _accountName.setAutoSave();

        ScPageRoot root;
        root = newPageRoot();
        root.css().padSpaced();

        installDialog(root);

        ScArray leftCol;
        leftCol = root.addColumn();

        _equalizeBox = leftCol.addBox();

        ScArray row;
        // review_aaron
        //        row = leftCol.addRow();
        row = _equalizeBox.addRow();

        installAccountUserSearchBox(row);
        installAccountUserTarget(row);
        installPasswordFrame(row);
        installAccountUserGrid(leftCol);

        return root;
    }

    private void installDialog(ScBox root)
    {
        _deleteDialog = root.addDialog();
        _deleteDialog.getHeaderBox().hide();
        _deleteDialog.getFooterBox().hide();
        _deleteDialog.setBodyHeight(125);

        ScBox body = _deleteDialog.getBodyBox();

        ScGroup group;
        group = body.addGroup("Are you sure you want to \n delete this account user?");

        ScDiv footer;
        footer = group.addButtonBoxRight();

        ScActionButton button1;
        button1 = footer.addButton("Go Back", newCloseAction());
        button1.setImage(MyButtonUrls.cancel());
        button1.setFlavorNegative();

        ScActionButton button2;
        button2 = footer.addButton("Delete", newDeleteAction());
        button2.setImage(MyButtonUrls.primary());
        button2.setFlavorPositive();
    }

    private void installAccountUserSearchBox(ScArray root)
    {
        _searchUserNameField = new ScAutoCompleteField();
        _searchUserNameField.setLabel("User name is ");
        _searchUserNameField.setCallback(newUserNameCallback());

        _searchAccountNameField = new ScAutoCompleteField();
        _searchAccountNameField.setLabel("Account name is ");
        _searchAccountNameField.setCallback(newAccountNameCallback());

        _searchTypeDropdown = MyAccount.Tools.newTypeDropdown();
        _searchTypeDropdown.setLabel("Account type is ");
        _searchTypeDropdown.addNullAnyPrefix();

        _searchRoleDropdown = MyAccountUser.Tools.newRoleDropdown();
        _searchRoleDropdown.setLabel("User role is ");
        _searchRoleDropdown.addNullAnyPrefix();

        ScFilterBox box;
        box = root.addFilterBox();
        box.setTitle("Filter Account Users ");

        ScFieldTable fields;
        fields = box.addFields();
        fields.add(_searchUserNameField);
        fields.add(_searchAccountNameField);
        fields.add(_searchTypeDropdown);
        fields.add(_searchRoleDropdown);

        box.setAction(newSearchAccountUserAction());

        _accountUserSearchBox = box;
    }

    private ScAutoCompleteCallbackIF newUserNameCallback()
    {
        return new ScAutoCompleteCallbackIF()
        {
            @Override
            public KmList<String> getOptionsFor(String term)
            {
                return getAutoCompleteUserNameOptions(term);
            }
        };
    }

    private KmList<String> getAutoCompleteUserNameOptions(String term)
    {
        KmList<String> v;
        v = new KmList<String>();

        KmList<MyUser> users;
        users = getAccess().findAllUsers();

        for ( MyUser e : users )
            if ( e.getName().toLowerCase().contains(term.toLowerCase()) )
                v.add(e.getName());

        return v;
    }

    private ScAutoCompleteCallbackIF newAccountNameCallback()
    {
        return new ScAutoCompleteCallbackIF()
        {
            @Override
            public KmList<String> getOptionsFor(String term)
            {
                return getAutoCompleteAccountNameOptions(term);
            }
        };
    }

    private KmList<String> getAutoCompleteAccountNameOptions(String term)
    {
        KmList<String> v;
        v = new KmList<String>();

        KmList<MyAccount> accounts;
        accounts = getAccess().findAllAccounts();

        for ( MyAccount e : accounts )
            if ( e.getName().toLowerCase().contains(term.toLowerCase()) )
                v.add(e.getName());

        return v;
    }

    private void installAccountUserGrid(ScArray row)
    {
        MyMetaAccountUser x = MyAccountUser.Meta;

        ScGroup group;
        group = row.addGroup();
        group.setTitle("Account Users");

        ScDiv right;
        right = group.getHeader().addFloatRight();
        right.css().pad5();
        right.addButton("Add", newShowAddAccountUserBoxAction());

        ScGridColumn<MyAccountUser> userEmail;
        userEmail = new ScGridColumn<MyAccountUser>();
        userEmail.setDisplayAdaptor(newUserEmailDisplayAdaptor());
        userEmail.setHeader("User Email");

        ScGridColumn<MyAccountUser> userRole;
        userRole = new ScGridColumn<MyAccountUser>();
        userRole.setDisplayAdaptor(newUserRoleDisplayAdaptor());
        userRole.setHeader("User Role");

        ScGridColumn<MyAccountUser> accountType;
        accountType = new ScGridColumn<MyAccountUser>();
        accountType.setDisplayAdaptor(newAccountTypeDisplayAdaptor());
        accountType.setHeader("Account Type");

        ScGrid<MyAccountUser> grid;
        grid = group.addGrid();
        grid.trackAll(_accountUserSearchBox);
        grid.setFilterFactory(newAccountUserFetcher());
        grid.addColumn(x.UserName).setCharacterWidth(8);
        grid.addColumn(userEmail);
        grid.addColumn(userRole).setCharacterWidth(8);
        grid.addColumn(x.AccountName).setCharacterWidth(18);
        grid.addColumn(accountType).setCharacterWidth(10);
        grid.addColumn(x.RoleName).setCharacterWidth(8);
        grid.addLinkColumn("View", newShowViewAccountUserBoxAction(), x.Uid).setCharacterWidth(5);
        grid.addLinkColumn("Delete", newShowDeleteDialogAction(), x.Uid).setCharacterWidth(5);

        _accountUserGrid = grid;
    }

    private KmAdaptorIF<MyAccountUser,String> newUserEmailDisplayAdaptor()
    {
        return new KmAdaptorIF<MyAccountUser,String>()
        {
            @Override
            public String getValue(MyAccountUser model)
            {
                if ( model.getUser() != null )
                    return model.getUser().getEmail();
                return "";
            }

            @Override
            public void setValue(MyAccountUser model, String value)
            {
                //none
            }
        };
    }

    private KmAdaptorIF<MyAccountUser,String> newUserRoleDisplayAdaptor()
    {
        return new KmAdaptorIF<MyAccountUser,String>()
        {
            @Override
            public String getValue(MyAccountUser model)
            {
                if ( model.getUser() != null )
                    return model.getUser().getRoleName();
                return "";
            }

            @Override
            public void setValue(MyAccountUser model, String value)
            {
                //none
            }
        };
    }

    private KmAdaptorIF<MyAccountUser,String> newAccountTypeDisplayAdaptor()
    {
        return new KmAdaptorIF<MyAccountUser,String>()
        {
            @Override
            public String getValue(MyAccountUser model)
            {
                if ( model.getAccount() != null )
                    return model.getAccount().getTypeName();
                return "";
            }

            @Override
            public void setValue(MyAccountUser model, String value)
            {
                //none
            }
        };
    }

    private KmFilterFactoryIF<MyAccountUser> newAccountUserFetcher()
    {
        return new KmFilterFactoryIF<MyAccountUser>()
        {
            @Override
            public KmFilterIF<MyAccountUser> createFilter()
            {
                return newAccountUserFilter();
            }
        };
    }

    private KmFilterIF<MyAccountUser> newAccountUserFilter()
    {
        String userName = _searchUserNameField.getValue();
        String accountName = _searchAccountNameField.getValue();

        MyAccountUserFilter f;
        f = new MyAccountUserFilter();
        f.sortAscending();

        if ( _searchUserNameField.hasValue() )
        {
            MyUser u = getUserDao().findName(userName);
            f.setUserUid(u.getUid());
        }

        if ( _searchAccountNameField.hasValue() )
        {
            MyAccount a = getAccountDao().findName(accountName);
            f.setAccountUid(a.getUid());
        }

        if ( _searchRoleDropdown.hasValue() )
            f.setRoleCode(_searchRoleDropdown.getStringValue());

        return f;
    }

    private void installAccountUserTarget(ScArray row)
    {
        _accountUserFrame = row.addFrame();

        installViewAccountUserFrame();
        installAddAccountUserFrame();
        installEditAccountUserFrame();
    }

    private void installPasswordFrame(ScArray row)
    {
        _passwordFrame = row.addFrame();

        ScActionIF saveAction = newAcceptAction();
        ScActionIF cancelAction = newAddAccountUserCancelAction();

        ScFrameChild frameChild;
        frameChild = _passwordFrame.createChild();

        ScForm form;
        form = addFormToFrame(saveAction, cancelAction, frameChild);
        _form = form;

        ScGroup group;
        group = form.addGroup("Set a Password");

        ScBox body;
        body = group.addBox();
        body.css().pad();

        _password1Field = new ScPasswordField();
        _password1Field.setRequired();

        _password2Field = new ScPasswordField();

        // todo_valerie: make this prettier
        ScBox chooseLabel;
        chooseLabel = group.addLabel("Choose a Password");
        chooseLabel.css().padTop();
        chooseLabel.css().padHorizontal();

        ScErrorBox chooseBox;
        chooseBox = group.addErrorBox();
        chooseBox.add(_password1Field);
        chooseBox.css().padHorizontal();

        ScBox reEnterLabel;
        reEnterLabel = group.addLabel("Re-enter Password");
        reEnterLabel.css().padTop();
        reEnterLabel.css().padHorizontal();

        ScErrorBox reEnterBox;
        reEnterBox = group.addErrorBox();
        reEnterBox.add(_password2Field);
        reEnterBox.css().padHorizontal();

        group.addDivider();

        ScDiv footer;
        footer = group.addButtonBoxRight();
        footer.addCancelButton(cancelAction);
        footer.addSubmitButton("Activate User");

        _passwordChild = frameChild;
    }

    private void installViewAccountUserFrame()
    {
        ScFrameChild frame;
        frame = _accountUserFrame.createChild();

        ScGroup group;
        group = frame.addGroup("View");

        ScDiv header;
        header = group.getHeader().addFloatRight();
        header.css().pad5();
        header.addButton("Edit", newShowEditAccountUserBoxAction());

        ScBox body;
        body = group.addBox();
        body.css().pad();

        _viewUserNameField = new ScText();
        _viewUserNameField.setLabel("User name is ");

        _viewUserEmailField = new ScText();
        _viewUserEmailField.setLabel("User's email is ");

        _viewUserVerifiedField = new ScText();
        _viewUserVerifiedField.setLabel("User's account verified ");

        _viewAccountName = new ScText();
        _viewAccountName.setLabel("Account name is ");

        _viewType = new ScText();
        _viewType.setLabel("Account type is ");

        _viewRole = new ScText();
        _viewRole.setLabel("Account User role is ");

        ScFieldTable fields;
        fields = body.addFields();
        fields.add(_viewUserNameField);
        fields.addSpace();
        fields.add(_viewUserEmailField);
        fields.addSpace();
        fields.add(_viewAccountName);
        fields.addSpace();
        fields.add(_viewType);
        fields.addSpace();
        fields.add(_viewRole);
        fields.addSpace();
        fields.add(_viewUserVerifiedField);

        _viewAccountUserChild = frame;
    }

    private void installAddAccountUserFrame()
    {
        ScActionIF saveAction = newAddAccountUserSaveAction();
        ScActionIF cancelAction = newAddAccountUserCancelAction();

        ScFrameChild frame;
        frame = _accountUserFrame.createChild();

        ScForm form;
        form = addFormToFrame(saveAction, cancelAction, frame);

        ScGroup group;
        group = form.addGroup("Add");

        ScBox body;
        body = group.addBox();
        body.css().pad();

        _addUserNameField = new ScAutoCompleteField();
        _addUserNameField.setLabel("User name is ");
        _addUserNameField.setCallback(newUserNameCallback());

        _addUserEmailField = new ScTextField();
        _addUserEmailField.setLabel("User's email is ");

        _addAccountNameField = new ScAutoCompleteField();
        _addAccountNameField.setLabel("Account name is ");
        _addAccountNameField.setCallback(newAccountNameCallback());

        _addTypeDropdown = MyAccount.Tools.newTypeDropdown();
        _addTypeDropdown.setLabel("Account type is ");
        _addTypeDropdown.addNullAnyPrefix();

        _addRoleDropdown = MyAccountUser.Tools.newRoleDropdown();
        _addRoleDropdown.setLabel("User role is ");
        _addRoleDropdown.addNullAnyPrefix();

        ScFieldTable fields;
        fields = body.addFields();
        fields.add(_addUserNameField);
        fields.add(_addUserEmailField);
        fields.add(_addAccountNameField);
        fields.add(_addTypeDropdown);
        fields.add(_addRoleDropdown);

        group.addDivider();

        addFooterToGroup(cancelAction, group);

        _addAccountUserChild = frame;
    }

    private void installEditAccountUserFrame()
    {
        ScActionIF saveAction = newEditAccountUserSaveAction();
        ScActionIF cancelAction = newEditAccountUserCancelAction();

        ScFrameChild frame;
        frame = _accountUserFrame.createChild();

        ScForm form;
        form = addFormToFrame(saveAction, cancelAction, frame);

        ScGroup group;
        group = form.addGroup("Edit");

        ScBox body;
        body = group.addBox();
        body.css().pad();

        _editUserNameField = new ScTextField();
        _editUserNameField.setLabel("User name is ");

        _editUserEmailField = new ScTextField();
        _editUserEmailField.setLabel("User's email is ");

        _editAccountNameField = new ScTextField();
        _editAccountNameField.setLabel("Account name is ");

        _editTypeDropdown = MyAccount.Tools.newTypeDropdown();
        _editTypeDropdown.setLabel("Account type is ");

        _editRoleDropdown = MyAccountUser.Tools.newRoleDropdown();
        _editRoleDropdown.setLabel("User role is ");

        ScFieldTable fields;
        fields = body.addFields();
        fields.add(_editUserNameField);
        fields.add(_editUserEmailField);
        fields.add(_editAccountNameField);
        fields.add(_editTypeDropdown);
        fields.add(_editRoleDropdown);

        group.addDivider();

        addFooterToGroup(cancelAction, group);

        _editAccountUserChild = frame;
    }

    //##################################################
    //# actions
    //##################################################

    private ScActionIF newShowDeleteDialogAction()
    {
        return new ScAction(this)
        {
            @Override
            public void handle()
            {
                handleShowDeleteDialog();
            }
        };
    }

    private ScActionIF newDeleteAction()
    {
        return new ScAction(this)
        {
            @Override
            public void handle()
            {
                handleDelete();
            }
        };
    }

    private ScActionIF newCloseAction()
    {
        return new ScAction(this)
        {
            @Override
            public void handle()
            {
                handleClose();
            }
        };
    }

    private ScActionIF newSearchAccountUserAction()
    {
        return new ScAction(this)
        {
            @Override
            public void handle()
            {
                handleSearchAccountUser();
            }
        };
    }

    private ScActionIF newShowAddAccountUserBoxAction()
    {
        return new ScAction(this)
        {
            @Override
            public void handle()
            {
                handleShowAddAccountUserBox();
            }
        };
    }

    private ScActionIF newShowViewAccountUserBoxAction()
    {
        return new ScAction(this)
        {
            @Override
            public void handle()
            {
                handleShowViewAccountUserBox();
            }
        };
    }

    private ScActionIF newAcceptAction()
    {
        return new ScAction(this)
        {
            @Override
            public void handle()
            {
                handleAccept();
            }
        };
    }

    private ScActionIF newShowEditAccountUserBoxAction()
    {
        return new ScAction(this)
        {
            @Override
            public void handle()
            {
                handleShowEditAccountUserBox();
            }
        };
    }

    private ScActionIF newAddAccountUserSaveAction()
    {
        return new ScAction(this)
        {
            @Override
            public void handle()
            {
                handleAddAccountUserSave();
            }
        };
    }

    private ScActionIF newAddAccountUserCancelAction()
    {
        return new ScAction(this)
        {
            @Override
            public void handle()
            {
                handleAddAccountUserCancel();
            }
        };
    }

    private ScActionIF newEditAccountUserSaveAction()
    {
        return new ScAction(this)
        {
            @Override
            public void handle()
            {
                handleEditAccountUserSave();
            }
        };
    }

    private ScActionIF newEditAccountUserCancelAction()
    {
        return new ScAction(this)
        {
            @Override
            public void handle()
            {
                handleEditAccountUserCancel();
            }
        };
    }

    //##################################################
    //# handles
    //##################################################

    private void handleShowDeleteDialog()
    {
        MyAccountUser e;
        e = getAccess().findAccountUserUid(getStringArgument());
        getPageSession().setAccountUser(e);

        _deleteDialog.ajaxOpen();
    }

    private void handleDelete()
    {
        MyAccountUser e;
        e = getPageSession().getAccountUser();
        e.deleteDao();

        _deleteDialog.ajaxClose();

        ajax().toast("Deleted account user %s", e.getUid());

        _accountUserGrid.ajaxReload();
    }

    private void handleClose()
    {
        _deleteDialog.ajaxClose();
    }

    private void handleSearchAccountUser()
    {
        _accountUserGrid.ajaxReload();
    }

    private void handleShowAddAccountUserBox()
    {
        _addAccountUserChild.ajaxPrint();
        _addAccountUserChild.ajax().focus();

        // review_aaron: 
        _accountUserFrame.ajax().defer();
        _equalizeBox.ajax().equalizeDecendentGroups();
    }

    private void handleShowViewAccountUserBox()
    {
        MyAccountUser au;
        au = getAccess().findAccountUserUid(getStringArgument());
        getPageSession().setAccountUser(au);

        MyUser u;
        u = au.getUser();
        getPageSession().setUser(u);

        MyAccount a;
        a = au.getAccount();
        getPageSession().setAccount(a);

        if ( u != null )
            _viewUserNameField.setValue(u.getName());

        if ( u != null )
            _viewUserEmailField.setValue(u.getEmail());

        if ( u != null )
            _viewUserVerifiedField.setValue(u.getVerified().toString());

        if ( a != null )
            _viewAccountName.setValue(a.getName());

        if ( a != null )
            _viewType.setValue(a.getType().getName());

        _viewRole.setValue(au.getRoleName());

        _viewAccountUserChild.ajaxPrint();

        // review_aaron: 
        _accountUserFrame.ajax().defer();
        _equalizeBox.ajax().equalizeDecendentGroups();
    }

    private void handleAccept()
    {
        ajax().hideAllErrors();
        ajax().focus();

        _form.validate();

        _password1Field.ajax().clearValue();
        _password2Field.ajax().clearValue();

        String p1 = _password1Field.getValue();
        String p2 = _password2Field.getValue();

        // fixme_valerie: not warning correctly
        if ( Kmu.isNotEqual(p1, p2) )
            _password1Field.error("Passwords did not match.");

        MyAccount findAccount;
        findAccount = getAccountDao().findName(getAccountName());

        MyUser user;

        // fixme_valerie: come back to this
        if ( getAccountName() == null )
            user = getUserDao().getNewUser(getUserName(), getUserEmail(), p1);
        else
            if ( findAccount == null )
                user = getUserDao().createNewUser(
                    getUserName(),
                    getUserEmail(),
                    p1,
                    getAccountName());
            else
                user = getUserDao().createNewUser(getUserName(), getUserEmail(), p1, findAccount);

        MyAccount a;
        a = getAccountDao().findName(getAccountName());
        a.saveDao();

        MyAccountUser au;
        au = getAccess().getAccountUserDao().findAccountUserFor(user, a);
        au.saveDao();

        if ( _addTypeDropdown.hasValue() )
            a.setTypeCode(_addTypeDropdown.getStringValue());

        if ( _addRoleDropdown.hasValue() )
            au.setRoleCode(_addRoleDropdown.getStringValue());

        _form.ajax().hide();
        _accountUserGrid.ajaxReload();
    }

    private void handleShowEditAccountUserBox()
    {
        MyAccountUser au;
        au = getPageSession().getAccountUser();

        MyUser u;
        u = getPageSession().getUser();

        MyAccount a;
        a = getPageSession().getAccount();

        if ( u != null )
            _editUserNameField.setValue(u.getName());

        if ( u != null )
            _editUserEmailField.setValue(u.getEmail());

        if ( a != null )
            _editAccountNameField.setValue(a.getName());

        if ( a != null )
            _editTypeDropdown.setValue(a.getType());

        _editRoleDropdown.setValue(au.getRole());

        _editAccountUserChild.applyFromModel(au);
        _editAccountUserChild.ajaxPrint();

        // review_aaron: 
        _accountUserFrame.ajax().defer();
        _equalizeBox.ajax().equalizeDecendentGroups();
    }

    private void handleAddAccountUserSave()
    {
        _addAccountUserChild.validate();

        if ( !_addUserNameField.hasValue() )
        {
            ajax().toast("Please enter a user name");
            return;
        }

        if ( !_addUserEmailField.hasValue() )
        {
            ajax().toast("Please enter the user's email");
            return;
        }

        setUserName(_addUserNameField.getValue());
        setUserEmail(_addUserEmailField.getValue());
        setAccountName(_addAccountNameField.getValue());

        if ( getAccountName() == null )
            setAccountName("Personal");

        MyUser u;
        u = getUserDao().findName(getUserName());

        if ( u == null )
        {
            _passwordChild.ajaxPrint();
            _passwordChild.ajax().focus();
            _passwordFrame.ajax().defer();
            _equalizeBox.ajax().equalizeDecendentGroups();
            return;
        }

        u.setName(getUserName());
        u.setEmail(getUserEmail());
        u.saveDao();

        MyAccount a;
        a = getAccountDao().findName(getAccountName());

        if ( a == null )
            a = getAccountDao().createNewAccount(getAccountName(), MyAccountType.Personal, u);

        a.setName(getAccountName());
        a.saveDao();

        MyAccountUser au;
        au = getAccess().getAccountUserDao().findAccountUserFor(u, a);

        if ( au == null )
            au = getAccess().getAccountUserDao().getNewAccountUser(u, a);

        au.setAccount(a);
        au.setUser(u);
        au.saveDao();

        if ( _addTypeDropdown.hasValue() )
            a.setTypeCode(_addTypeDropdown.getStringValue());

        if ( _addRoleDropdown.hasValue() )
            au.setRoleCode(_addRoleDropdown.getStringValue());

        _accountUserGrid.ajaxReload();
    }

    private void handleAddAccountUserCancel()
    {
        _accountUserFrame.ajaxClear();
    }

    private void handleEditAccountUserSave()
    {
        _editAccountUserChild.validate();

        if ( !_editUserNameField.hasValue() )
        {
            ajax().toast("Please enter a user name");
            return;
        }

        if ( !_editUserEmailField.hasValue() )
        {
            ajax().toast("Please enter the user's email");
            return;
        }

        if ( !_editAccountNameField.hasValue() )
        {
            ajax().toast("Please enter an account name");
            return;
        }

        MyUser u;
        u = getPageSession().getUser();

        if ( u == null )
            u = new MyUser();

        u.setName(_editUserNameField.getValue());
        u.setEmail(_editUserEmailField.getValue());

        MyAccount a;
        a = getPageSession().getAccount();

        if ( a == null )
            a = new MyAccount();

        a.setName(_editAccountNameField.getValue());

        MyAccountUser au;
        au = getPageSession().getAccountUser();
        au.setAccount(a);
        au.setUser(u);

        if ( _editTypeDropdown.hasValue() )
            a.setTypeCode(_editTypeDropdown.getStringValue());

        if ( _editRoleDropdown.hasValue() )
            au.setRoleCode(_editRoleDropdown.getStringValue());

        u.saveDao();
        a.saveDao();
        au.saveDao();

        _accountUserGrid.ajaxReload();
    }

    private void handleEditAccountUserCancel()
    {
        _accountUserFrame.ajaxClear();
    }

    //##################################################
    //# convenience
    //##################################################

    private ScForm addFormToFrame(ScActionIF saveAction, ScActionIF cancelAction, ScFrameChild frame)
    {
        ScForm e;
        e = frame.addForm();
        e.setDefaultAction(saveAction);
        e.onEscape().run(cancelAction);
        return e;
    }

    private void addFooterToGroup(ScActionIF cancelAction, ScGroup group)
    {
        ScDiv e;
        e = group.addButtonBoxRight();
        e.addCancelButton(cancelAction);
        e.addSubmitButton("Save");
    }

    private MyUserDao getUserDao()
    {
        return getAccess().getUserDao();
    }

    private MyAccountDao getAccountDao()
    {
        return getAccess().getAccountDao();
    }

    private String getUserName()
    {
        return _userName.getValue();
    }

    private void setUserName(String e)
    {
        _userName.setValue(e);
    }

    private String getUserEmail()
    {
        return _userEmail.getValue();
    }

    private void setUserEmail(String e)
    {
        _userEmail.setValue(e);
    }

    private String getAccountName()
    {
        return _accountName.getValue();
    }

    private void setAccountName(String e)
    {
        _accountName.setValue(e);
    }
}
