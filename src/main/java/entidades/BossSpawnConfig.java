package entidades;

import com.flowpowered.math.vector.Vector3i;

import java.util.ArrayList;

public class BossSpawnConfig {

    private String nombreLugar;
    private ArrayList<String> pokemon;
    private Vector3i posCentral;

    public BossSpawnConfig(String nombreLugar, Vector3i posCentral) {
        this.nombreLugar = nombreLugar;
        this.posCentral = posCentral;
    }

    public BossSpawnConfig(String nombreLugar, ArrayList<String> pokemon, Vector3i posCentral) {
        this.nombreLugar = nombreLugar;
        this.pokemon = pokemon;
        this.posCentral = posCentral;
    }

    public String getNombreLugar() {
        return this.nombreLugar;
    }

    public ArrayList<String> getPokemon() {
        return this.pokemon;
    }

    public Vector3i getPosCentral() {
        return this.posCentral;
    }

    public void setPokemon(ArrayList<String> pokemon) {
        this.pokemon = pokemon;
    }

}
