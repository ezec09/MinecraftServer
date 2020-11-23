package tasks.otros;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.manipulator.mutable.entity.FoodData;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

public class AlimentarTask implements Consumer<Task>  {

    @Override
    public void accept(Task task) {
        Collection<Player> jugadoresOnline = Sponge.getServer().getOnlinePlayers();
        Iterator var2 = jugadoresOnline.iterator();

        while(var2.hasNext()) {
            Player player = (Player)var2.next();
            FoodData foodData = player.getFoodData();
            Value<Integer> f = foodData.foodLevel().set(foodData.foodLevel().getDefault());
            Value<Double> d = foodData.saturation().set(foodData.saturation().getDefault());
            foodData.set(new BaseValue[]{f, d});
            player.offer(foodData);
        }
    }
}
