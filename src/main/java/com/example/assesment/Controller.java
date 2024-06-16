package com.example.assesment;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class Controller {

    @PostMapping("/wisselgeld")
    public WisselgeldResponse berekenWisselgeld(@RequestBody wisselgeldRequest request) {
        double aankoopBedrag = request.getAankoopBedrag();
        double betaaldBedrag = request.getBetaaldBedrag();
        double wisselgeld = betaaldBedrag - aankoopBedrag;

        // Wisselgeld afronden op de dichtstbijzijnde 0.05
        wisselgeld = Math.round(wisselgeld * 20.0) / 20.0;

        return new WisselgeldResponse(wisselgeld, berekenAantallen(wisselgeld));
    }

    private Map<String, Integer> berekenAantallen(double wisselgeld) {
        Map<String, Integer> wisselgeldMap = new HashMap<>();
        int[] briefjes = {50, 20, 10, 5};
        double[] munten = {2, 1, 0.50, 0.20, 0.10, 0.05};

        for (int briefje : briefjes) {
            int aantal = (int) (wisselgeld / briefje);
            wisselgeld -= aantal * briefje;
            if (aantal > 0) {
                wisselgeldMap.put(briefje + " euro", aantal);
            }
        }

        for (double munt : munten) {
            int aantal = (int) (wisselgeld / munt);
            wisselgeld -= aantal * munt;
            if (aantal > 0) {
                wisselgeldMap.put((double)(munt) + " euro", aantal);
            }
        }

        return wisselgeldMap;
    }

    static class wisselgeldRequest {
        private double aankoopBedrag;
        private double betaaldBedrag;

        public double getAankoopBedrag() {
            return aankoopBedrag;
        }

        public void setAankoopBedrag(double aankoopBedrag) {
            this.aankoopBedrag = aankoopBedrag;
        }

        public double getBetaaldBedrag() {
            return betaaldBedrag;
        }

        public void setBetaaldBedrag(double betaaldBedrag) {
            this.betaaldBedrag = betaaldBedrag;
        }
    }

    static class WisselgeldResponse {
        private double totaalWisselgeld;
        private Map<String, Integer> wisselgeld;

        public WisselgeldResponse(double totaalWisselgeld, Map<String, Integer> wisselgeld) {
            this.totaalWisselgeld = totaalWisselgeld;
            this.wisselgeld = wisselgeld;
        }

        public double getTotaalWisselgeld() {
            return totaalWisselgeld;
        }

        public void setTotaalWisselgeld(double totaalWisselgeld) {
            this.totaalWisselgeld = totaalWisselgeld;
        }

        public Map<String, Integer> getWisselgeld() {
            return wisselgeld;
        }

        public void setWisselgeld(Map<String, Integer> wisselgeld) {
            this.wisselgeld = wisselgeld;
        }
    }
}
