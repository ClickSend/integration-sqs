SQS JAVA PRODUCER:

How to run it:

1- import this project as a maven project from any ide

2- replace the below parameters with your own:
    private final static String QUEUE_URL ="";
    public static final String ACCESS_KEY_ID = "";
    public static final String ACCESS_KEY_SECRET = "";

3- than run Main.java with credentials as parameters with first as the aws_access_key_id and second as aws_secret_access_key since it has a public static void main method. 

Alternatively after replacing AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_SECRET with your credentails you can run it with "mvn clean install"
on command line.
