import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
   


public class Main {

    // Insert your AWS queue url
    private final static String QUEUE_URL ="https://sqs.ap-southeast-2.amazonaws.com/193962410929/Permission_check";//
    // sqs send msg, least privelage principle,create queue

    static ProfileCredentialsProvider acc = ProfileCredentialsProvider.builder().build();


    public static void main(String[] args) {
        
        // Reads your AWS credentials from credentials file: Access_key_ID, Access_key_secret
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                acc.resolveCredentials().accessKeyId(),
                acc.resolveCredentials().secretAccessKey());

        // Insert the your region, for information follow: https://docs.aws.amazon.com/AmazonRDS/latest/UserGuide/Concepts.RegionsAndAvailabilityZones.html
        SqsClient sqsClient = SqsClient.builder()
                .region(Region.AP_SOUTHEAST_2)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();

        SendMessageResponse response= sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(QUEUE_URL)

        // Your message body:- 
        // to: reciever number, 
        // body: your message, 
        // from: by default it is shared number, you can also choose your business name, or your own number,
        // for more information regarding "from" follow: https://help.clicksend.com/article/4kgj7krx00-what-is-a-sender-id-or-sender-number
                .messageBody("{" +
                        "\"to\":\"+61410719979\"," +
                        "\"source\":\"sqs\"," +
                        "\"body\":\"hello from permisson test java vscode\"," +
                        "\"from\":\"Testing\""+
                        "}")
                .build());
        System.out.println(response);
    } 
}