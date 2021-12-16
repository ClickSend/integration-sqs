import java.util.ArrayList;
import java.util.List;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;

public class Main {

    //change with your own queue url
    private final static String QUEUE_URL ="https://sqs.region.amazonaws.com/XXXXXXXXXXXX/queuename";

    static ProfileCredentialsProvider acc = ProfileCredentialsProvider.builder().build();

    // Reads your AWS credentials from credentials file: Access_key_ID, Access_key_secret
    static AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                acc.resolveCredentials().accessKeyId(),
                acc.resolveCredentials().secretAccessKey());

    public static SendMessageResponse sendMessage(String phoneNumber, String message, SqsClient sqsClient){

        SendMessageResponse response= sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(QUEUE_URL)

    // Your message body:- 
        // to: reciever number, use the "numbers" list to change the recipients
        // body: your message, use the "messageToSend" string to change the content of message
        // from: by default it is shared number, you can also choose your business name, or your own number,
        // for more information regarding "from" follow: https://help.clicksend.com/article/4kgj7krx00-what-is-a-sender-id-or-sender-number
                .messageBody("{" +
                        "\"to\":\""+phoneNumber+"\"," +
                        "\"source\":\"SQS\"," +
                        "\"body\":\""+message+"\","+
                        "\"from\":\"Testing\""+"}")
                .build());
        return response;
    }

    public static void main(String[] args) {
 
        // Insert the your region, for information follow: https://docs.aws.amazon.com/AmazonRDS/latest/UserGuide/Concepts.RegionsAndAvailabilityZones.html
        SqsClient sqsClient = SqsClient.builder()
                .region(Region.Region)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();

        List<String> numbers = new ArrayList<>();

        // Add the recipient's numbers
        numbers.add("+111111111");
        numbers.add("+505555555");
        numbers.add("+606666666");

        // Your message
        String messagesToSend = "Hello from AWS SQS java";

        for(String number : numbers){
            System.out.println(sendMessage(number,messagesToSend,sqsClient));
        }
    }
}
