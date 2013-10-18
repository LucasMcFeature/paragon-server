package com.kodemore.twitter;

import com.kodemore.collection.KmList;
import com.kodemore.json.KmJsonArray;

/**
 * I am used connect to Twitter make various requests.
 * 
 * You will need to do the following to use this class:
 *      1.  Sign up for a Twitter account.
 *      2.  Visit dev.twitter.com and sign in as your user.
 *      3.  Create a new application for testing.
 *      4.  Retrieve the consumer key, consumer secret, access token (auth token) 
 *          and access token (secret auth token secret) from the application information page.
 *      5.  Update the main method with the token and screts from the previous step.
 */
public class KmTwitterSearchUserTest
{
    public static void main(String[] args)
    {

        /**
         * review_wyatt (steve) please look at this. it is working.
         */

        KmTwitterSearchUsersRequest req;
        req = new KmTwitterSearchUsersRequest();

        // You need to enter valid keys...
        req.setConsumerKey("8r0G5xBFcYdckiP2DCuNA");
        req.setConsumerSecret("tBkfioOW8d3TosIzvqBzywQBu74ffRi2R0nwpssZvU");
        req.setAuthToken("1935624583-W4xwK2jYMbr4mx5g02KqiGoLAjgYvp29d3S4FKr");
        req.setAuthSecret("KuSse3LKnuV0vkgs57bHxgcqtjNzixd0SlwP5TPI0");

        req.setQuery("@accucodeSteve");
        req.submit();

        KmJsonArray responseJson = req.getResponseJson();

        KmList<KmTwitterUser> responseUsers = req.getResponseUsers();

        System.out.println("Response");
        System.out.println(responseJson);

        System.out.println("LIST OF USERS");
        System.out.println("==================================");
        for ( KmTwitterUser u : responseUsers )
            System.out.println(u.getName());
        System.out.println("==================================");
        System.out.println(responseUsers.getFirst().getStatus());
        System.out.println(responseUsers.getFirst().getStatus().getPlace().getBoundingbox());
    }
}
