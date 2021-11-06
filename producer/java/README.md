SQS JAVA PRODUCER:

How to run it:

1- import this project as a maven project from any ide

2- replace the below parameters with your own:
    private final static String QUEUE_URL ="";
    public static final String ACCESS_KEY_ID = "";
    public static final String ACCESS_KEY_SECRET = "";

3- than run Main.java since it has a public static void main method. 
Alternatively you can run it with mvn compile exec:java -Dexec.mainClass="Main"
