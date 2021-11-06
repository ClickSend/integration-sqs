import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

public class Main {

    //Put your own queue url
    private final static String QUEUE_URL ="https://sqs.eu-west-1.amazonaws.com/023280750523/emaileventsqueue";

    public static void main(String[] args) {

        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                ACCESS_KEY_ID,
                ACCESS_KEY_SECRET);

        SqsClient sqsClient = SqsClient.builder()
                .region(Region.EU_WEST_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();

        SendMessageResponse response= sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(QUEUE_URL)
                .messageBody("{" +
                        "\"to\":\"+505555555\"," +
                        "\"source\":\"sdk\"," +
                        "\"body\":\"hello from java client\"" +
                        "}")
                .build());
        System.out.println(response);
    }
}
