package com.example.pokemon_cross_reference;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;

    JsonParser parser;

    private String json;

    private boolean causedError = false;

    private String printList = "";

    private String runningList = "";

    private String type11;

    private String type21;

    private String ability1;

    private String move11;

    private String move21;

    private String move31;

    private String move41;

    private boolean sameType = false;

    private boolean holdYourHorses = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView text = findViewById(R.id.textView);
        final EditText type10 = findViewById(R.id.type1);
        final EditText type20 = findViewById(R.id.type2);
        final EditText ability0 = findViewById(R.id.ability);
        final EditText move10 = findViewById(R.id.move1);
        final EditText move20 = findViewById(R.id.move2);
        final EditText move30 = findViewById(R.id.move3);
        final EditText move40 = findViewById(R.id.move4);
        text.setMovementMethod(new ScrollingMovementMethod());
        requestQueue = Volley.newRequestQueue(this);
        ((Button) findViewById(R.id.button)).setOnClickListener((v) -> {
            if (holdYourHorses) {
                holdYourHorses = false;
                type11 = type10.getText().toString();
                type21 = type20.getText().toString();
                ability1 = ability0.getText().toString();
                move11 = move10.getText().toString();
                move21 = move20.getText().toString();
                move31 = move30.getText().toString();
                move41 = move40.getText().toString();
                if (type11.equals(type21)) {
                    sameType = true;
                }
                api(requestQueue, type11, type21, ability1, move11, move21, move31, move41, sameType);
                text.setText(printList);
                if (runningList.equals("")) {
                    printList = "No such Pokemon exists.";
                } else {
                    printList = runningList;
                }
                sameType = false;
                runningList = "";
                holdYourHorses = true;
            }
        });
    }
    public void api(RequestQueue requestQueue,String type1,String type2, String ability, String move1, String move2, String move3, String move4, boolean sameType){
        String urlBase = "https://pokeapi.co/api/v2/pokemon/";
        String url;
        if (sameType){
            for (int i = 1; i <808; i++) {
                url = urlBase + String.valueOf(i) + "/";
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        json = response;
                        parser = new JsonParser();
                        JsonObject result = parser.parse(json).getAsJsonObject();
                        JsonArray PokemonTypes = result.getAsJsonArray("types");
                        JsonArray PokemonMoves = result.getAsJsonArray("moves");
                        JsonArray PokemonAbilities = result.getAsJsonArray("abilities");
                        String[] typeArray = new String[PokemonTypes.size()];
                        String[] moveArray = new String[PokemonMoves.size()];
                        String[] abilityArray = new String[PokemonAbilities.size()];
                        for (int j = 0; j < PokemonTypes.size(); j++) {
                            JsonObject type = PokemonTypes.get(j).getAsJsonObject();
                            JsonObject typeObject = type.getAsJsonObject("type");
                            String typeName = typeObject.get("name").toString();
                            typeName = typeName.substring(1, typeName.length() - 1);
                            typeArray[j] = typeName;
                        }
                        for (int j = 0; j < PokemonMoves.size(); j++) {
                            JsonObject move = PokemonMoves.get(j).getAsJsonObject();
                            JsonObject moveObject = move.getAsJsonObject("move");
                            String moveName = moveObject.get("name").toString();
                            moveName = moveName.substring(1, moveName.length() - 1);
                            moveArray[j] = moveName;
                        }for (int j = 0; j < PokemonAbilities.size(); j++) {
                            JsonObject ability = PokemonAbilities.get(j).getAsJsonObject();
                            JsonObject abilityObject = ability.getAsJsonObject("ability");
                            String abilityName = abilityObject.get("name").toString();
                            abilityName = abilityName.substring(1, abilityName.length() - 1);
                            abilityArray[j] = abilityName;
                        }
                        String name = result.getAsJsonArray("forms").get(0).getAsJsonObject().get("name").toString();
                        name = name.substring(1, name.length() - 1);
                        if (checkTypeSame(type11, typeArray)) {
                            if (checkTypeSame(type21, typeArray)) {
                                if (checkAbility(ability1, abilityArray)) {
                                    if (checkMove(move11, moveArray)) {
                                        if (checkMove(move21, moveArray)) {
                                            if (checkMove(move31, moveArray)) {
                                                if (checkMove(move41, moveArray)) {
                                                    runningList = runningList + name + "\n";
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        json = error.getMessage();
                        causedError = true;
                    }
                });
                requestQueue.add(stringRequest);
                if (causedError) {
                    causedError = false;
                    return;
                }
            }
        } else {
            for (int i = 1; i < 808; i++) {
                url = urlBase + String.valueOf(i) + "/";
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        json = response;
                        parser = new JsonParser();
                        JsonObject result = parser.parse(json).getAsJsonObject();
                        JsonArray PokemonTypes = result.getAsJsonArray("types");
                        JsonArray PokemonMoves = result.getAsJsonArray("moves");
                        JsonArray PokemonAbilities = result.getAsJsonArray("abilities");
                        String[] typeArray = new String[PokemonTypes.size()];
                        String[] moveArray = new String[PokemonMoves.size()];
                        String[] abilityArray = new String[PokemonAbilities.size()];
                        for (int j = 0; j < PokemonTypes.size(); j++) {
                            JsonObject type = PokemonTypes.get(j).getAsJsonObject();
                            JsonObject typeObject = type.getAsJsonObject("type");
                            String typeName = typeObject.get("name").toString();
                            typeName = typeName.substring(1, typeName.length() - 1);
                            typeArray[j] = typeName;
                        }
                        for (int j = 0; j < PokemonMoves.size(); j++) {
                            JsonObject move = PokemonMoves.get(j).getAsJsonObject();
                            JsonObject moveObject = move.getAsJsonObject("move");
                            String moveName = moveObject.get("name").toString();
                            moveName = moveName.substring(1, moveName.length() - 1);
                            moveArray[j] = moveName;
                        }for (int j = 0; j < PokemonAbilities.size(); j++) {
                            JsonObject ability = PokemonAbilities.get(j).getAsJsonObject();
                            JsonObject abilityObject = ability.getAsJsonObject("ability");
                            String abilityName = abilityObject.get("name").toString();
                            abilityName = abilityName.substring(1, abilityName.length() - 1);
                            abilityArray[j] = abilityName;
                        }
                        String name = result.getAsJsonArray("forms").get(0).getAsJsonObject().get("name").toString();
                        name = name.substring(1, name.length() - 1);
                        if (checkType(type11, typeArray)) {
                            if (checkType(type21, typeArray)) {
                                if (checkAbility(ability1, abilityArray)) {
                                    if (checkMove(move11, moveArray)) {
                                        if (checkMove(move21, moveArray)) {
                                            if (checkMove(move31, moveArray)) {
                                                if (checkMove(move41, moveArray)) {
                                                    runningList = runningList + name + "\n";
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        json = error.getMessage();
                        causedError = true;
                    }
                });
                requestQueue.add(stringRequest);
                if (causedError) {
                    causedError = false;
                    return;
                }
            }
        }
    }
    boolean checkType(String toCheck, String[] list) {
        if (toCheck.equals("Type 1") || toCheck.equals("Type 2")) {
            return true;
        }
        for (int i = 0; i < list.length; i++) {
            if (toCheck.equals(list[i])) {
                return true;
            }
        }
        return false;
    }
    boolean checkMove(String toCheck, String[] list) {
        if (toCheck.equals("Move 1") || toCheck.equals("Move 2") || toCheck.equals("Move 3") || toCheck.equals("Move 4")) {
            return true;
        }
        for (int i = 0; i < list.length; i++) {
            if (toCheck.equals(list[i])) {
                return true;
            }
        }
        return false;
    }
    boolean checkAbility(String toCheck, String[] list) {
        if (toCheck.equals("Ability")) {
            return true;
        }
        for (int i = 0; i < list.length; i++) {
            if (toCheck.equals(list[i])) {
                return true;
            }
        }
        return false;
    }
    boolean checkTypeSame(String toCheck, String[] list) {
        if (list.length == 2) {
            return false;
        }
        if (toCheck.equals(list[0])) {
            return true;
        }
        return false;
    }
    //https://pokeapi.co/
}
