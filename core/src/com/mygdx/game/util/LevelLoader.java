package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Level;
import com.mygdx.game.personaje.Meta;
import com.mygdx.game.personaje.Obstaculo;
import com.mygdx.game.personaje.Plataforma;
import com.mygdx.game.personaje.Salida;
import com.mygdx.game.personaje.Vida;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.util.Comparator;

public class LevelLoader {

    public static final String TAG = LevelLoader.class.toString();

    public static Level load(String levelName, Viewport viewport, Constants.Difficulty difficulty) {

        Level level = new Level(viewport,difficulty);

        String path = Constants.LEVEL_DIR + File.separator + levelName + "." + Constants.LEVEL_FILE_EXTENSION;

        FileHandle file = Gdx.files.internal(path);
        JSONParser parser = new JSONParser();

        try {

            JSONObject rootJsonObject = (JSONObject) parser.parse(file.reader());
            JSONObject composite = (JSONObject) rootJsonObject.get(Constants.LEVEL_COMPOSITE);
            JSONArray platforms = (JSONArray) composite.get(Constants.LEVEL_9PATCHES);
            loadPlatforms(platforms, level);
            JSONArray nonPlatformObjects = (JSONArray) composite.get(Constants.LEVEL_IMAGES);
            loadNonPlatformEntities(nonPlatformObjects,level);


        } catch (Exception ex) {

            Gdx.app.error(TAG, ex.getMessage());

            Gdx.app.error(TAG, Constants.LEVEL_ERROR_MESSAGE);
        }

        return level;
    }
    private static float safeGetFloat(JSONObject object, String key){
        Number number = (Number) object.get(key);
        return (number == null) ? 0 : number.floatValue();
    }

    private static void loadPlatforms(JSONArray array, Level level) {

        Array<Plataforma> platformArray = new Array<Plataforma>();
        Array<Obstaculo> obstaculoArray = new Array<Obstaculo>();

        for (Object object : array) {
            final JSONObject platformObject = (JSONObject) object;

            final float x = safeGetFloat(platformObject, Constants.LEVEL_X_KEY);

            final float y = safeGetFloat(platformObject, Constants.LEVEL_Y_KEY);

            final float width = ((Number) platformObject.get(Constants.LEVEL_WIDTH_KEY)).floatValue();

            final float height = ((Number) platformObject.get(Constants.LEVEL_HEIGHT_KEY)).floatValue();

            Gdx.app.log(TAG,"Loaded a platform at x = " + x + " y = " + (y + height) + " w = " + width + " h = " + height);

            if(platformObject.get(Constants.LEVEL_IMAGENAME_KEY).equals(Constants.OBSTACULO_SPRITE)){
                final Obstaculo obstaculo = new Obstaculo (x, y + height, width, height);
                obstaculoArray.add(obstaculo);
            }else if (platformObject.get(Constants.LEVEL_IMAGENAME_KEY).equals(Constants.PLATFORM_SPRITE)){
                final Plataforma platform = new Plataforma(x, y + height, width, height);
                platformArray.add(platform);
            }
        }

        platformArray.sort(new Comparator<Plataforma>() {
            @Override
            public int compare(Plataforma o1, Plataforma o2) {
                if (o1.top < o2.top) {
                    return 1;
                } else if (o1.top > o2.top) {
                    return -1;
                }
                return 0;
            }
        });


        level.getObstaculos().addAll(obstaculoArray);
        level.getPlataforma().addAll(platformArray);
    }


    public static void loadNonPlatformEntities(JSONArray nonPlatformObjects, Level level){
        for (Object o : nonPlatformObjects) {

            JSONObject item = (JSONObject) o;
            Vector2 lowerLeftCorner = new Vector2();

            final float x = safeGetFloat(item, Constants.LEVEL_X_KEY);
            final float y = safeGetFloat(item, Constants.LEVEL_Y_KEY);

            lowerLeftCorner = new Vector2(x, y);

            if (item.get(Constants.LEVEL_IMAGENAME_KEY).equals(Constants.SALIDA_SPRITE)) {
                final Vector2 salidaPosition = lowerLeftCorner.add(Constants.SALIDA_POSITION);
                Gdx.app.log(TAG, "Loaded the salida at " + salidaPosition);
                level.setSalida(new Salida(salidaPosition));

            }else if(item.get(Constants.LEVEL_IMAGENAME_KEY).equals(Constants.META_SPRITE)) {
                final Vector2 metaPosition = lowerLeftCorner.add(Constants.META_POSITION);
                Gdx.app.log(TAG, "Loaded the meta at " + metaPosition);
                level.setMeta(new Meta(metaPosition));

            }else if (item.get(Constants.LEVEL_IMAGENAME_KEY).equals(Constants.VIDA_SPRITE)) {
                final Vector2 vidaPosition = lowerLeftCorner.add(Constants.VIDA_POSITION);
                Gdx.app.log(TAG, "Loaded a live at " + vidaPosition);
                level.getVidas().add(new Vida(vidaPosition/*, level*/));
            }
        }
    }

}
