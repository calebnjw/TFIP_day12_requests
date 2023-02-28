package workshop.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import workshop.exception.RandomNumberException;
import workshop.model.Generate;

@Controller
@RequestMapping(path = "/rand")
public class GenerateRandomController {
  @GetMapping(path = "/show")
  public String showRandForm(Model m) {
    Generate g = new Generate();
    m.addAttribute("generateObject", g);
    return "generate";
  }

  @GetMapping(path = "/generate")
  public String generate(@RequestParam Integer numberVal, Model m) {
    this.randomiseNumber(m, numberVal.intValue());
    return "result";
  }

  public void randomiseNumber(Model m, int numGen) {
    int maxGen = 30;
    String[] numImages = new String[maxGen + 1];

    if (numGen < 1 || numGen > maxGen) {
      throw new RandomNumberException();
    }

    for (int i = 0; i < maxGen; i++) {
      numImages[i] = "number" + i + ".jpg";
    }

    List<String> selectedImg = new ArrayList<String>();
    Random rand = new Random();
    // hashed set will not allow duplicates
    // so it will show three unique numbers
    Set<Integer> uniqueResult = new LinkedHashSet<Integer>();

    while (uniqueResult.size() < numGen) {
      Integer value = rand.nextInt(maxGen);
      if (value != null) {
        uniqueResult.add(value);
      }
    }

    Iterator<Integer> i = uniqueResult.iterator();
    Integer current = null;
    while (i.hasNext()) {
      current = i.next();
      selectedImg.add(numImages[current.intValue()]);
    }

    m.addAttribute("randomNumCount", numGen);
    m.addAttribute("randomNumResult", selectedImg);
  }
}
