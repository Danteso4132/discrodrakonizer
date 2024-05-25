package com.danteso.discrodrakonizer.utils;

import com.danteso.discrodrakonizer.bot.actions.BotAction;
import com.danteso.discrodrakonizer.bot.actions.Weightable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class WeightedOperations {


    public static <E extends Weightable> Optional<E> getRandomWeightedObject(Collection<E> collection){
        List<E> list = new ArrayList<>(collection);
        int totalWeight = list.stream().mapToInt(Weightable::getWeighting).sum();
        int countWeight = 0;
        int i = ThreadLocalRandom.current().nextInt(0, totalWeight);
        for (E element: list){
            countWeight += element.getWeighting();
            if (countWeight >= i){
                return Optional.of(element);
            }
        }
        return Optional.empty();
    }
}
