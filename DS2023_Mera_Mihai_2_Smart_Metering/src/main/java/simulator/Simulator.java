package simulator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.FileController;
import controller.RabbitController;
import model.Measurement;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Properties;

public class Simulator implements Runnable{

    private int milliseconds;
    private RabbitController rabbitController;
    private FileController fileController;
    private Boolean running;
    private Long deviceId;

    public Simulator(int milliseconds) {
        this.milliseconds = milliseconds;
        this.rabbitController = new RabbitController();
        this.fileController = new FileController("src/main/resources/sensor.csv");
        running = true;

        Properties properties = new Properties();
        try(InputStream input = new FileInputStream("src/main/resources/config.properties")){
            properties.load(input);
        }catch(IOException e){
            e.printStackTrace();
        }

        this.deviceId = Long.parseLong(properties.getProperty("deviceID"));
    }

    @Override
    public void run() {
        while(running) {
            String message = fileController.readLine();

            if(message == null) {
                running = false;
                break;
            }else{
                rabbitController.send(prepareMessage(message));
            }

            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String prepareMessage(String message){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Measurement measurement = new Measurement(timestamp.getTime(), deviceId, Double.parseDouble(message));
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.writeValueAsString(measurement);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

    }
}
