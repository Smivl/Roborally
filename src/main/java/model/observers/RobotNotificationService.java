package model.observers;

import model.observers.RobotObserver;
import model.observers.RobotObserverAlerts;

import java.util.*;

/*
public class RobotNotificationService {
    private final Map<RobotObserverAlerts, List<RobotObserver>> observers;

    public RobotNotificationService() {
        observers = new HashMap<>();
        Arrays.stream(RobotObserverAlerts.values()).forEach(alert->observers.put(alert, new ArrayList<RobotObserver>()));
    }

    public void subscribe(RobotObserverAlerts alert, RobotObserver observer){
        observers.get(alert).add(observer);

    }
    public void unsubscribe(RobotObserverAlerts alert, RobotObserver observer){
        observers.get(alert).remove(observer);
    }
    public void notify(RobotObserverAlerts alert){
        observers.get(alert).forEach(RobotObserver::update);
    }

}

 */
