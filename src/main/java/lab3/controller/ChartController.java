package lab3.controller;

import lab3.model.Model;
import lab3.model.Point;
import lab3.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ChartController {

    private final static Logger logger = LoggerFactory.getLogger(ChartController.class);
    private Model model;

    @Autowired
    public ChartController(Model model) {
        this.model = model;
    }

    @RequestMapping(path = "/step", method = RequestMethod.GET)
    public ResponseEntity<Model> getChart() {
        logger.info("step {}", model.getK());
        model.step();
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @RequestMapping(path = "/getEvTimePoints", method = RequestMethod.GET)
    public ResponseEntity<List<Point>> getEvTimePoints() {
        List<Point> evPoints = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            Model model1 = new Model(model.getLENGTH(), i, model.getMU());
            model1.simulate();
            evPoints.add(new Point(model1.getLAMBDA(), model1.getEvTime()));
        }
        return new ResponseEntity<>(evPoints, HttpStatus.OK);
    }

    @RequestMapping(path = "/getFreeTimePoints", method = RequestMethod.GET)
    public ResponseEntity<List<Point>> getFreeTimePoints() {
        List<Point> freeTimePoints = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            Model model1 = new Model(model.getLENGTH(), i, model.getMU());
            model1.simulate();
            freeTimePoints.add(new Point(model1.getLAMBDA(), model1.getFreeTime() / model1.getT()));
        }
        return new ResponseEntity<>(freeTimePoints, HttpStatus.OK);
    }


    @RequestMapping(path = "/dist", method = RequestMethod.GET)
    public ResponseEntity<List<Point>> getDist() {
        Model model1 = new Model(model.getLENGTH(), model.getLAMBDA(), model.getMU());
        model1.simulate();

        return new ResponseEntity<>(aggregate(getDistPoints(model1)), HttpStatus.OK);
    }

    private List<Point> getDistPoints(Model model1) {
        return model1.getSolvedTasks().stream().map(Task::getWaitTime).collect(
                Collectors.groupingBy(task -> task, Collectors.counting())
        ).entrySet().stream().map(
                entry -> new Point(entry.getKey(), entry.getValue())
        )
                .sorted(Comparator.comparing(Point::getX))
                .collect(Collectors.toList());
    }

    private List<Point> aggregate(List<Point> points) {
        List<Point> distPoints = new ArrayList<>();

        double h =(points.get(points.size() - 1).getX() - points.get(0).getX()) / 3000;
        int i = 0;
        Iterator<Point> itr = points.iterator();
        while(itr.hasNext()) {
            Point newPoint = new Point((i + 0.5) * h , 0);
            Point point = itr.next();
            while (i* h <= point.getX() && point.getX() <= (i + 1) * h && itr.hasNext()) {
                newPoint.setY(newPoint.getY() + point.getY());
                point = itr.next();
            }

            if (newPoint.getY() != 0)
                distPoints.add(newPoint);
            i++;
        }

        return distPoints;
    }
}
