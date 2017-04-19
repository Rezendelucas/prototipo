package com.mygdx.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.Utils.Constants;


/**
 * Created by LucasRezende on 21/11/2016.
 */
public class Assets implements Disposable, AssetErrorListener {

    public static  final String Tag = Assets.class.getName();
    public  static final  Assets instance = new Assets();

    private AssetManager assetManager;
    public AssetFonts fontes;
    public AssetWhiteBall whiteball;
    public AssetBall ball;
    public AssetBorda borda;
    public AssetGoldCoin goldCoin;


    private Assets(){};

    public void init(AssetManager assetManager){
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
        assetManager.finishLoading();
        Gdx.app.debug(Tag,"# assets carregados: " + assetManager.getAssetNames().size);
        for(String a : assetManager.getAssetNames()) {
            Gdx.app.debug(Tag, "asset: " + a);
        }
        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
        for (Texture t : atlas.getTextures()){
            t.setFilter(TextureFilter.Linear,TextureFilter.Linear);
        }
        fontes = new AssetFonts();
        whiteball = new AssetWhiteBall(atlas);
        borda = new AssetBorda(atlas);
        goldCoin = new AssetGoldCoin(atlas);
        ball = new AssetBall(atlas);
    }


    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(Tag, "Asset nao foi carregada ",(Exception)throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        fontes.defaultBig.dispose();
        fontes.defaultNormal.dispose();
        fontes.defaultSmall.dispose();
    }

    public class AssetWhiteBall{
       public final  AtlasRegion whiteball;

       public  AssetWhiteBall (TextureAtlas atlas){
           whiteball = atlas.findRegion("white_ball");
       }
    }

    public class AssetBall{
        public final  AtlasRegion ball;

        public  AssetBall (TextureAtlas atlas){
            ball = atlas.findRegion("ball");
        }
    }

    public class AssetBorda{
        public final AtlasRegion corner;
        public final AtlasRegion middle;

        public  AssetBorda(TextureAtlas atlas){
            corner = atlas.findRegion("borda_corner");
            middle = atlas.findRegion("borda_middle");
        }
    }

    public class AssetGoldCoin{
        public final  AtlasRegion goldCoin;

        public  AssetGoldCoin (TextureAtlas atlas){
            goldCoin = atlas.findRegion("item_gold_coin");
        }
    }

    public class AssetFonts {
        public final BitmapFont defaultSmall;
        public final BitmapFont defaultNormal;
        public final BitmapFont defaultBig;

        public AssetFonts () {
            // create three fonts using Libgdx's 15px bitmap font
            defaultSmall = new BitmapFont(Gdx.files.internal(Constants.FONT_SOURCE), true);
            defaultNormal = new BitmapFont(Gdx.files.internal(Constants.FONT_SOURCE), true);
            defaultBig = new BitmapFont(Gdx.files.internal(Constants.FONT_SOURCE), true);
            // set font sizes
      //       defaultSmall.setScale(0.75f);
      //      defaultNormal.setScale(1.0f);
      //       defaultBig.setScale(2.0f);
            // enable linear texture filtering for smooth fonts
            defaultSmall.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
            //defaultSmall.setScale(Scale.scale(0.75,0.75f).);
            defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
            defaultBig.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

        }

    }
}

