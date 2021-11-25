package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    @FXML
    private Button btnStartStop;
    @FXML
    private Button btnResetReszido;
    @FXML
    private Label stopper;
    @FXML
    private ListView<String> reszidoLista;
    private boolean running;
    private Timer stopperTimer;
    private LocalDateTime startTime;

    @FXML
    public void initialize() {
        running = false;
    }

    @FXML
    public void startStopClick() {
        if (running) {
            stopperStop();
        } else {
            stopperStart();
        }
    }

    private void stopperStart() {
        btnStartStop.setText("Stop");
        btnResetReszido.setText("Részidő");
        running = true;
        stopperTimer = new Timer();
        startTime = LocalDateTime.now();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Duration elteltIdo = Duration.between(startTime, LocalDateTime.now());
                int perc = elteltIdo.toMinutesPart();
                int masodperc = elteltIdo.toSecondsPart();
                int ms = elteltIdo.toMillisPart();
                Platform.runLater(() -> stopper.setText(String.format("%02d:%02d:%03d", perc, masodperc, ms)));
            }
        };
        stopperTimer.schedule(timerTask,1,1);
    }

    private void stopperStop() {
        btnStartStop.setText("Start");
        btnResetReszido.setText("Reset");
        running = false;
        stopperTimer.cancel();
    }

    @FXML
    public void resetReszidoClick() {
        if (running) {
            reszido();
        } else {
            reset();
        }
    }

    private void reset() {
        stopper.setText("00:00.000");
        reszidoLista.getItems().clear();
    }

    private void reszido() {
        String reszido = stopper.getText();
        reszidoLista.getItems().add(reszido);
    }
}
