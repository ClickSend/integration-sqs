# ClickSend AWS SQS Integration

### Sending SMS through Amazon SQS over clicksend v3 api:

This tutorial shows how to integrate AWS SQS service with ClickSend API using
AWS Lambda. For troubleshooting purposes please contact our support team using
the following link: [Support](https://www.clicksend.com/au/help/contact/)

There are three main components used in sending an SMS using the ClickSend API.

1.  The event producer which will create and send the events to AWS SQS.

2.  AWS SQS which will deliver the events produced by the producer to the Lambda
    event listener.

3.  AWS Lambda which will be triggered by SQS events and send SMS over ClickSend
    API.

#### Prerequisites:

-   You must have a ClickSend account -
    [Sign-Up](https://dashboard.clicksend.com/#/dashboard/home)

-   You must have an AWS account -
    [Sign-Up](https://aws.amazon.com/free/?trk=ps_a134p000003yhj4AAA&trkCampaign=acq_paid_search_brand&sc_channel=ps&sc_campaign=acquisition_AU&sc_publisher=google&sc_category=core&sc_country=AU&sc_geo=APAC&sc_outcome=Acquisition&sc_detail=aws%20account&sc_content=Account_e&sc_matchtype=e&sc_segment=454645973173&sc_medium=ACQ-P%7CPS-GO%7CBrand%7CDesktop%7CSU%7CAWS%7CCore%7CAU%7CEN%7CText&s_kwcid=AL!4422!3!454645973173!e!!g!!aws%20account&ef_id=EAIaIQobChMIntC6sczV9AIVSQ8rCh3QMwcPEAAYASAAEgJtcvD_BwE:G:s&s_kwcid=AL!4422!3!454645973173!e!!g!!aws%20account&all-free-tier.sort-by=item.additionalFields.SortRank&all-free-tier.sort-order=asc&awsf.Free%20Tier%20Types=*all&awsf.Free%20Tier%20Categories=*all)

-   You must have a GitHub account -
    [Sign-Up](https://github.com/signup?ref_cta=Sign+up&ref_loc=header+logged+out&ref_page=%2F%3Cuser-name%3E%2F%3Crepo-name%3E&source=header-repo&source_repo=ClickSend%2Fintegration-sqs)
    
-   You must have GitHub CLI installed on your PC -  [GitHub-CLI](https://cli.github.com/manual/installation)

-   You must have AWS CLI installed on your PC - [AWS-CLI](https://aws.amazon.com/cli/) 

-   You must have NodeJs installed on your PC - [NodeJS](https://nodejs.org/en/)

After you have fulfilled the above prerequisites, follow these steps to install
the dependencies and run the program:

**Setting up your computer**

1.  Open your command line / shell
     <p float="left">
     <img src="https://user-images.githubusercontent.com/66475561/146469403-8eb6a158-72b5-47ea-83d3-362651133a1d.png" alt="window_cmd" width="450" height="255"/>   <img src="https://user-images.githubusercontent.com/66475561/146470139-ef736eb4-89a0-4b0e-8b84-feeaf29984ec.png" alt="terminal" width="450" height="255"/>
     </p>


2.  Follow this link and run the commands on terminal or cmd to install GitHub
    cli: [GitHub-CLI](https://cli.github.com/manual/installation)

3.  Clone the GitHub repo by following this SQS-repo [link](https://github.com/ClickSend/integration-sqs) or download the zip
    file.
    
    <img width="450" alt="Github Clone" src="https://user-images.githubusercontent.com/66475561/146472454-87d107f2-1911-4b0a-bd3b-17f7e45d765e.png">

**AWS Dependencies and configuration**

4.  Browse to producer nodejs folder from command line / shell.
5.  Install AWS-SDK and uuid.

    ```sh
    npm install
    ```
    For example, on Window’s cmd:
    
    <img width="450" alt="AWS SDK UUID" src="https://user-images.githubusercontent.com/66475561/146473087-2b543f7c-ee40-420f-86b2-1f9a5b4e9abc.PNG">
    

**AWS credentials and required permissions:**

6.  There are two way to obtain the credentials and permissions:
    
    #### Option 1: If you are working under an organisation
    
    Ask your AWS administartor for your credentials:
    
    <img width="450" alt="Group" src="https://user-images.githubusercontent.com/66475561/146483186-0456729e-831d-4249-b634-c9c818cd0462.png">
    
    Ask your AWS administrator to grant you the permissions attached in the policy.json file under the nodejs folder by creating a new policy.
    
    <img width="450" alt="JSON" src="https://user-images.githubusercontent.com/66475561/146481575-0affe5c8-75b9-43b6-9eb5-7c8157c2a938.png">
    
    #### Option 2: You are the administrator
    
    In case you are the administrator or if you have created a new AWS account. Copy your credentials from top right of your console:
    
    <img width="450" alt="Group" src="https://user-images.githubusercontent.com/66475561/147034794-e4270027-0f80-4e07-bee1-a46c2e40a409.png">
    
7. Configure your AWS CLI by creating and setting up .AWS folder
    credential(obtained from above) and config(your region) files (make sure
    these files does not have any extensions). Follow these links for more
    instructions:
    
    a. [cli-config-files](https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-files.html)  
    b. [config-file-location](https://docs.aws.amazon.com/sdkref/latest/guide/file-location.html)   
    For example:
   
    <img width="450" alt="CLI Credentials" src="https://user-images.githubusercontent.com/66475561/146483502-e1ed06d8-cabe-4491-9038-e762b28d2361.png">
    
**Creating the/an AWS-SQS queue**

8. To setup your AWS-SQS, go to your AWS console and search for “SQS” and select the first option:
     
    <img width="450" alt="AWS SQS" src="https://user-images.githubusercontent.com/66475561/146493941-7b523e0c-dec6-4558-821b-ca2c749ee024.png">

9. Click on “Create queue”:

    <img width="450" alt="Create queue" src="https://user-images.githubusercontent.com/66475561/146495132-08783deb-168d-4da3-be18-aff245f663a9.png">

10. Select a name for the queue as per your convenience. No need to change any settings/configuration/access policy the default settings are enough.

    <img width="450" alt="Queue name" src="https://user-images.githubusercontent.com/66475561/146495940-1161134c-7109-46ec-8201-e5c623635133.png">

11. After selecting the name, press the create queue button.

    <img width="450" alt="Queue Name" src="https://user-images.githubusercontent.com/66475561/146496972-bc6b6c4e-929e-45f8-b15b-ba7513a636b4.png">

12. After creating the queue, a window stating the following message should appear on your screen. Copy the queue URL.
    
    <img width="450" alt="Queue" src="https://user-images.githubusercontent.com/66475561/147175011-db471e36-5247-4f2a-91ef-65a8106ef12e.png">

**Sending SMS to an AWS Queue:** 

13. #### This guide shows two ways to produce and send SMS to AWS SQS queue

    #### Option 1: If you want to use Java

    Open the producer java folder as maven project from any IDE. For example, on
    visual studio code:
    <img width="450" alt="JAVA" src="https://user-images.githubusercontent.com/66475561/147174292-45df6e67-0ff7-4269-af14-3b3b52e80bb4.png">

    a. In case if you do not have maven installed on your IDE, you should first install it from your IDE’s extension page. For example, on visual studio code:

    <img width="450" alt="Maven" src="https://user-images.githubusercontent.com/66475561/146703600-e072047f-9021-466a-997b-e92c4d39ff09.png">

    b. Or download and install from: [Apache-Maven](https://maven.apache.org/download.cgi)

    Replace the AWS SQS queue URL in the main.java file with the created queue URL:

    <img width="450" alt="Queue URL" src="https://user-images.githubusercontent.com/66475561/147174170-2c18276c-3c73-42c2-8b74-360cc7c97903.png">

    Insert your region and the content of message body:

    <img width="450" alt="Region" src="https://user-images.githubusercontent.com/66475561/146704604-e3057a4c-100a-49f3-9a74-4edc75d80598.png">

    Run the program. You should see a message response like this in your IDE’s terminal:

    <img width="450" alt="Success" src="https://user-images.githubusercontent.com/66475561/146708174-2f8ea5cf-d2b5-4606-8b84-3a835cdf965b.png">

    #### Option 2: If you want to use NodeJS

    Open the producer nodejs folder from any IDE.

    <img width="450" alt="NodeJS" src="https://user-images.githubusercontent.com/66475561/146708363-96a41f05-21eb-4a02-9e25-138cbc41cd4f.png">

    Replace the AWS SQS queue URL in the main.js file with the created queue URL, For example, on visual studio code:

    <img width="450" alt="Replacing URL" src="https://user-images.githubusercontent.com/66475561/147174125-6d9eaab0-ef7e-426f-ae1b-a2e7b7c0596f.png">

14. Change the content of the message body:

    <img width="450" alt="Message" src="https://user-images.githubusercontent.com/66475561/147173985-7b86b894-69d0-4a22-9db9-06f03b157cca.png">

15. Execute the "main.js" file by running the following command on command line / shell: 

    ```sh
    node main.js
    ```
    
    You should see a success message like this in IDE's terminal:
    
    <img width="450" alt="Success" src="https://user-images.githubusercontent.com/66475561/146876211-395e912a-ce74-4d63-b70e-9505284be13f.png">  

    

**To test whether the message is in the queue**

16. To check whether this message has reached the queue, press on the
    “Send and receive messages” button.
    
    <img width="450" alt="SMS queue" src="https://user-images.githubusercontent.com/66475561/147175049-94474414-92d0-4d32-819d-8b67e62e85a6.png">
    
17. Then click on the “Poll for messages”, there should be messages available.
    
    <img width="450" alt="Poll" src="https://user-images.githubusercontent.com/66475561/146886327-fa84dd73-1641-4320-85ca-1426ad866391.png">

**Creating a Lambda event listener:**

Now we need to create an AWS Lambda function which will accept and send the
messages to the ClickSend API.

18. In the search bar of your AWS console enter and select Lambda.

    <img width="450" alt="Lambda" src="https://user-images.githubusercontent.com/66475561/146886884-f268d9a8-442c-47a5-b97e-55071e877bda.png">

19. Click on create function

    <img width="450" alt="Function" src="https://user-images.githubusercontent.com/66475561/146886923-cd9f4409-93d4-4991-a6cc-fe4abd2d6ce8.png">

20. Enter a function name

    <img width="450" alt="Function Name" src="https://user-images.githubusercontent.com/66475561/146893423-504fd725-61fc-4ae3-83ff-607c1a5d3775.png">

21. In the permission section search and add “Amazon SQS poller permissions”
    under the “Policy templates” field. Click on “Create function”.

    <img width="450" alt="Policy" src="https://user-images.githubusercontent.com/66475561/146892832-1b75c162-7388-467a-93d3-118c5ac2810e.png">

**Setting up the Lambda with our consumer**

Now that our Lambda is created, we need to add code to Lambda that enables
Lambda to send the messages via ClickSend API.

22. Browse to consumer/Lambda folder on your computer from terminal/cmd.

    <img width="450" alt="Lambda Folder" src="https://user-images.githubusercontent.com/66475561/146889369-2b37cd85-7584-4881-a68a-b91064b476b7.png">


23. Install ClickSend dependencies by running the these commands:
  
    -   Install ClickSend SDK for sending messages from the command line / shell
    
        ```sh
        npm i clicksend
        ```
    -   Install TypeScript which will be used to compile the code:
        ```sh
        sudo npm install typescript
        ```
    -   For compiling typescript into javascript:
        ```sh
        sudo npm add request http bluebird @types/node
        tsc --target es5 /node_modules/clicksend/api.ts
        ```
    -   For more information, please follow [the setup guide in GitHub](https://github.com/ClickSend/clicksend-nodejs)

24. After the installation, you should have these files/folders in your consumer folder.
    
    <img width="450" alt="File Check" src="https://user-images.githubusercontent.com/66475561/146891182-400bfc83-2914-4d79-b9ad-afb0bb9caaaa.png">

25. Compress these 3 files/folder into a zip file, choose any name.

26. Replace the “sqssmsconsumer” with the Lambda function's name and “Lambda.zip” with your zip file name. Run the command from your command line / shell:

    ```sh
    aws lambda update-function-code --function-name sqssmsconsumer --zip-file fileb://Lambda.zip
    ```

27. Refresh the page, you should see notification stating:

    <img width="450" alt="Notification" src="https://user-images.githubusercontent.com/66475561/146892193-53293f95-d2b1-4298-b9e6-61e532f68902.png">

**Setting up your ClickSend credentials on AWS Lambda function:**

28. Get your ClickSend credential, follow this link:
    [ClickSend_Credentials](https://dashboard.clicksend.com/account/subaccounts)

29. Set your ClickSend credentials on AWS Lambda, we need to create two environment variables on your Lambda function. Go to configuration tab and click on "Environment variables". Then click on "Edit".

    <img width="450" alt="ENV Variables" src="https://user-images.githubusercontent.com/66475561/147016256-6edb127d-c100-4c8c-a24d-fd48bdaa017f.png">

30. Click on "Add environment variable":

    <img width="450" alt="Add Variables" src="https://user-images.githubusercontent.com/66475561/147019516-0ad0d49f-5a77-4a15-a0d3-71fc1e940491.png">

31. Create the two environment variables named “username” and “api_key” and insert your ClickSend credentials in the value column and click save.

    <img width="450" alt="Two Variables" src="https://user-images.githubusercontent.com/66475561/147020744-a3c2d5c2-2c5c-430a-b495-9974d18c3271.png">

32. You should be able to see the created environment variable.

    <img width="450" alt="Added Variables" src="https://user-images.githubusercontent.com/66475561/147021059-3969a5b1-7c0f-48a9-b504-99c2cb3d8fb1.png">

**Creating trigger for the Lambda function:**

33. Click on the "Add trigger":

    <img width="450" alt="Add Trigger" src="https://user-images.githubusercontent.com/66475561/147021250-b1e08da1-7f9c-46b5-9041-7ed81307d036.png">
    
34. Search and select "SQS" from search bar

    <img width="450" alt="Search SQS" src="https://user-images.githubusercontent.com/66475561/147021720-bb420443-b0bd-4901-825e-07bf5d23cf80.png">

35. Search and select your created queue from the SQS queue search bar.

    <img width="450" alt="Created queue" src="https://user-images.githubusercontent.com/66475561/147175159-bc75ba94-278a-42fa-94f4-500787b2bfb4.png">
    
36. Click on the “Add trigger” button. You should the following message:

    <img width="450" alt="Added trigger" src="https://user-images.githubusercontent.com/66475561/147023491-d809534d-6ad5-42a5-b466-3b6d403e6dd1.png">
    
37. Done. Now everything is set up and configured.

38. Now whenever you want to send a message to someone, just change messagebody, to & from in the one of producers and run.

39. You can also see logs of your sent messages on the CloudWatch logs tab. If the log of messages does not appear here, click on “View logs in CloudWatch” button.
    
    <img width="450" alt="Logs" src="https://user-images.githubusercontent.com/66475561/147173859-6dbbc1dd-6cda-4677-98a3-670e984606b1.png">
