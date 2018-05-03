package lab3.controller;

import lab3.model.Model;
import lab3.model.Point;
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
import java.util.List;

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

    @RequestMapping(path = "/simulate", method = RequestMethod.GET)
    public ResponseEntity<List<Point>[]> simulate() {
        List<Point> evPoints = new ArrayList<>();
        List<Point> ratioPoints = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            Model model1 = new Model(model.getLENGTH(), i, model.getMU());
            model1.simulate();
            evPoints.add(new Point(model1.getLAMBDA(), model1.getEvTime()));
            ratioPoints.add(new Point(model1.getLAMBDA(), model1.getReactionTime()));
        }
        return new ResponseEntity<>(new List[]{evPoints, ratioPoints}, HttpStatus.OK);
    }
}
