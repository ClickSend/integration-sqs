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

#### Dependencies (Please Install it as per the instructions):

1.  ClickSend:

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

2.  AWS

    -   Install AWS SDK and AWS uuid for sending the request to SQS

        ```sh
        npm install
        ```
        (uses package.json from integration-SQS/producer/nodejs to
        check the AWS SDK and AWS uuid dependencies)

    -   Install AWS-CLI for making requests to SQS, follow this link:
        [AWS-CLI](https://aws.amazon.com/cli/)

    -   For more information regarding AWS node, please follow this link:
        [AWS-Node](https://docs.aws.amazon.com/sdk-for-javascript/v2/developer-guide/getting-started-nodejs.html)

After you have fulfilled the above prerequisites, follow these steps to install
the dependencies and run the program:

**Setting up your computer**

1.  Open your command line / shell
     <p float="left">
     <img src="https://user-images.githubusercontent.com/66475561/146469403-8eb6a158-72b5-47ea-83d3-362651133a1d.png" alt="window_cmd" width="450" height="255"/> <img src="https://user-images.githubusercontent.com/66475561/146470139-ef736eb4-89a0-4b0e-8b84-feeaf29984ec.png" alt="terminal" width="450" height="255"/>
     </p>


2.  Follow this link and run the commands on terminal or cmd to install GitHub
    cli: [GitHub-CLI](https://cli.github.com/manual/installation)

3.  Clone the GitHub repo by following the SQS-repo link or download the zip
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
    

**To obtain and your AWS credentials and required permissions:**

6.  Log in to AWS Console, go to IAM console

7.  Click on this [Link](https://console.aws.amazon.com/iamv2/home#/users). Click "next".
    
    <img width="450" alt="ADD USER" src="https://user-images.githubusercontent.com/66475561/146476797-a941daee-f59a-4f97-ae61-c4443ef3481d.png">
    
8.  Click on “Create group”:

    <img width="450" alt="Create Group" src="https://user-images.githubusercontent.com/66475561/146478032-94c70ccb-8520-449a-84ef-ff146a3efc2e.png">
    

9. Click on “Create policy”:

    <img width="450" alt="Create Policy" src="https://user-images.githubusercontent.com/66475561/146482022-3f634d69-293f-4342-b312-a15546053ee3.png">

10. Replace the contents of JSON tab with policy.json under the NodeJS producer
    folder and click on “Next”:
    
    <img width="450" alt="JSON" src="https://user-images.githubusercontent.com/66475561/146481575-0affe5c8-75b9-43b6-9eb5-7c8157c2a938.png">

11. After creating the new policy, select the created policy under the policy name column and
    click on “Create group”.
    
    <img width="450" alt="Group" src="https://user-images.githubusercontent.com/66475561/146482270-a5274703-daab-44a5-8e9c-8c44ad280381.png">

12. Click on the "next" for the next two pages.

13. On the last page, copy the Access key ID and Secret access key

    <img width="450" alt="Group" src="https://user-images.githubusercontent.com/66475561/146483186-0456729e-831d-4249-b634-c9c818cd0462.png">
    
14. Configure your AWS CLI by creating and setting up .AWS folder
    credential(obtained from above) and config(your region) files (make sure
    these files does not have any extensions). Follow these links for more
    instructions:
    
    a. [cli-config-files](https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-files.html)  
    b. [config-file-location](https://docs.aws.amazon.com/sdkref/latest/guide/file-location.html)   
    For example:
   
    <img width="450" alt="CLI Credentials" src="https://user-images.githubusercontent.com/66475561/146483502-e1ed06d8-cabe-4491-9038-e762b28d2361.png">
    
**Creating the/an AWS-SQS queue**

15. To setup your AWS-SQS, go to your AWS console and search for “SQS” and select the first option:
     
    <img width="450" alt="AWS SQS" src="https://user-images.githubusercontent.com/66475561/146493941-7b523e0c-dec6-4558-821b-ca2c749ee024.png">

16. Click on “Create queue”:

    <img width="450" alt="Create queue" src="https://user-images.githubusercontent.com/66475561/146495132-08783deb-168d-4da3-be18-aff245f663a9.png">

17. Select a name for the queue as per your convenience. No need to change any settings/configuration/access policy the default settings are enough.

    <img width="450" alt="Queue name" src="https://user-images.githubusercontent.com/66475561/146495940-1161134c-7109-46ec-8201-e5c623635133.png">

18. After selecting the name, press the create queue button.

    <img width="450" alt="Queue Name" src="https://user-images.githubusercontent.com/66475561/146496972-bc6b6c4e-929e-45f8-b15b-ba7513a636b4.png">

19. After creating the queue, a window stating the following message should appear on your screen. Copy the queue URL.
    
    <img width="450" alt="Queue" src="https://user-images.githubusercontent.com/66475561/146497637-7bf579db-c13b-406b-993f-cce05c1ddb97.png">

**Sending SMS to an AWS Queue:** 

**This guide shows two ways to produce and send SMS to AWS SQS queue**

**20(a). If you want to use Java**

    Open the producer java folder as maven project from any IDE. For example, on
    visual studio code:
    <img width="450" alt="JAVA" src="https://user-images.githubusercontent.com/66475561/146703412-f079c596-2197-4fa6-a245-3b39721fd953.png">

    In case you do not have maven installed on your IDE, you should first
    install it from your IDE’s extension page. For example, on visual studio
    code:

    <img width="450" alt="Maven" src="https://user-images.githubusercontent.com/66475561/146703600-e072047f-9021-466a-997b-e92c4d39ff09.png">

ii. Or download and install from: [Apache-Maven](https://maven.apache.org/download.cgi)

Replace the AWS SQS queue URL in the main.java file with the created queue URL:

<img width="450" alt="Queue URL" src="https://user-images.githubusercontent.com/66475561/146704099-d16c5c8a-a590-4228-bed0-d46da6123615.png">

Insert your region and the content of message body:

<img width="450" alt="Region" src="https://user-images.githubusercontent.com/66475561/146704604-e3057a4c-100a-49f3-9a74-4edc75d80598.png">

Run the program. You should see a message response like this in your IDE’s
terminal:

<img width="450" alt="Success" src="https://user-images.githubusercontent.com/66475561/146708174-2f8ea5cf-d2b5-4606-8b84-3a835cdf965b.png">

**21(b). If you want to use NodeJS**

    Open the producer nodejs folder from any IDE.

    <img width="450" alt="NodeJS" src="https://user-images.githubusercontent.com/66475561/146708363-96a41f05-21eb-4a02-9e25-138cbc41cd4f.png">

Replace the AWS SQS queue URL in the main.js file with the created queue URL, For example, on visual studio code:

<img width="450" alt="Replacing URL" src="https://user-images.githubusercontent.com/66475561/146708363-96a41f05-21eb-4a02-9e25-138cbc41cd4f.png">

27.  ![Text Description automatically
    generated](media/ca8c0814160177e010266acbeeceed45.png)Change the content of
    the message body:

28.  To execute the program, enter the node main.js command in the terminal and
    run it. You should see

    ![](media/1d750f4756035856d2b980a3fbf37c6d.png)a message response like this
    in your IDE’s terminal:

**To test the message delivery to queue**

1.  To check whether this message has been sent to a/the queue, press on the
    “Send and receive messages” button.

    ![Graphical user interface, application, Teams Description automatically
    generated](media/39492de01b69250d63f234f7eed95415.png)

2.  Then click on the “Poll for messages”, there should be messages
    available.![Graphical user interface, application, Teams Description
    automatically generated](media/5a5fce2f0602791fd704f15b428291f3.png)

**Creating a Lambda event listener:**

Now we need to create an AWS Lambda function which will accept and send the
messages to the ClickSend API.

1.  In the search bar of your AWS console enter and select Lambda.

    ![Graphical user interface, application Description automatically
    generated](media/c8dd6655631434f51c795e3ad76fe8e6.png)

2.  Click on create function

    ![Graphical user interface, application Description automatically
    generated](media/2094e7c8821460b691011664f28580c3.png)

3.  Enter a function name![Graphical user interface, text, application, email
    Description automatically
    generated](media/15522d25cfd5764847a014b56a06e044.png)

4.  In the permission section search and add “Amazon SQS poller permissions”
    under the “Policy templates” field. Click on “Create function”.

    ![Graphical user interface, text, application, Teams Description
    automatically generated](media/78fe240ef875fe9e9960d77844a7b6fa.png)

**Setting up the Lambda with our consumer**

Now that our Lambda is created, we need to add code to Lambda that enables
Lambda to send the messages via ClickSend API.

1.  Browse to consumer/Lambda folder on your computer from terminal/cmd.

    ![](media/c74cda0d7eab97bac158e03f6bfdec58.png)

2.  Install ClickSend dependencies by running the “Dependencies” commands of
    both ClickSend from the 1st page on terminal or cmd.

3.  After the installation, you should have these files/folders in your consumer
    folder.![A picture containing text Description automatically
    generated](media/169b49758f8236e070aef73e95a1ece7.png)

4.  Compress this 3 files/folder into a zip file, choose any name.

5.  Now run the following command from your terminal/cmd. Replace the
    “sqssmseventconsumer” with your Lambda function name created above and
    “Lambda.zip” with your zip file name.

    aws lambda update-function-code --function-name sqssmseventconsumer
    \--zip-file fileb://Lambda.zip

6.  Refresh the AWS page, you should see notification stating:

    ![Graphical user interface, text, application, email, Teams Description
    automatically generated](media/dbb5c7d20146e7b4db335b76f093ded6.png)

**Setting up your ClickSend credentials on AWS Lambda function:**

1.  To get your ClickSend credential, follow this link:
    [ClickSend_Credentials](https://dashboard.clicksend.com/account/subaccounts)

2.  To set your ClickSend credentials on AWS Lambda, we need to create two
    environment variables on your Lambda function. Go to configuration tab and
    click on environment variables. Then click on edit.![Graphical user
    interface, application, Teams Description automatically
    generated](media/1a455b7a3e599ab801bfc5ca88dcced5.png)

3.  Click on add environment variable![Graphical user interface, text,
    application, Teams Description automatically
    generated](media/363b905bc2fb55c41bf7fc1368972d38.png)

4.  Create the two environment variables named “username” and “api_key” and
    insert your ClickSend credentials in the value column and click
    save.![Graphical user interface, application Description automatically
    generated](media/731880ee81483e0e38ede1635bd675b0.png)

5.  You should be able to see the created environment variable.

    ![Graphical user interface, text, application Description automatically
    generated](media/c4701103aefda696f508fbd8f70d99a3.png)

**Creating trigger for the Lambda function:**

1.  Click on the add trigger button

    ![Graphical user interface, application Description automatically
    generated](media/63d7d4bbe11ff91e501f4597b3069894.png)

1.  Select SQS in search bar

    ![Graphical user interface, application, Word Description automatically
    generated](media/8877ee7796c87e0daf9b0027e6e636d9.png)

2.  Search and select your created queue from the SQS queue search bar.

3.  Click on the “Add trigger” button. You should the following
    message:![Graphical user interface, application Description automatically
    generated](media/ff7b5487455e3e713b8a4d76e0c33dab.png)

    ![Graphical user interface, application Description automatically
    generated](media/4bb7811e3a9cf13d0b6972deef2e8921.png)

4.  Done. Now everything is set up and configured.

5.  Now whenever you want to send a message to someone, just change messagebody,
    to & from in the one of producers and run.

6.  You can also see logs of your sent messages on the CloudWatch logs tab. If
    the log of messages does not appear here, click on “View logs in CloudWatch”
    button.![Graphical user interface, text, application, email Description
    automatically generated](media/a9565513b43147d34abdc46cecb8175f.png)


