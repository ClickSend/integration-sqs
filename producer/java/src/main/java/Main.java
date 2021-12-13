import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

public class Main {

    //change with your own queue url
    private final static String QUEUE_URL ="https://sqs.eu-west-1.amazonaws.com/023280750523/smseventsqueue";

    public static SendMessageResponse sendMessage(String phoneNumber, String message, SqsClient sqsClient){

        SendMessageResponse response= sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(QUEUE_URL)
                .messageBody("{" +
                        "\"to\":\""+phoneNumber+"\"," +
                        "\"source\":\"sdk\"," +
                        "\"body\":\""+message+"\"}")
                .build());
        return response;
    }

    public static void main(String[] args) {

        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                args[0],
                args[1]);
        SqsClient sqsClient = SqsClient.builder()
                .region(Region.EU_WEST_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();

        List<String> numbers = new ArrayList<>();
        numbers.add("+404444444");
        numbers.add("+505555555");
        numbers.add("+606666666");
        String messagesToSend = "Hello from aws";

        for(String number : numbers){
            System.out.println(sendMessage(number,messagesToSend,sqsClient));
        }
    }
}
