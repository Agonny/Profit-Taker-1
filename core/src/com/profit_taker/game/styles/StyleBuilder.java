package com.profit_taker.game.styles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class StyleBuilder {

    public BitmapFont createFont(int size){

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("general_font/general_font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.spaceX = -1;
        parameter.spaceY = 0;
        parameter.padBottom = 1;
        parameter.padLeft = 1;
        parameter.padRight = 1;
        parameter.padTop = 1;
        parameter.size = size;
        parameter.color = Color.BLACK;
        BitmapFont font= generator.generateFont(parameter);
        return font;
    }

    public Slider.SliderStyle createSlider(float scale){
        Texture background_tx= new Texture(Gdx.files.internal("slider_skin/slider_background.png"));
        Texture knob_tx = new Texture(Gdx.files.internal("slider_skin/slider_knob.png"));

        Drawable background_dr = new TextureRegionDrawable(new TextureRegion(background_tx));
        Drawable knob_dr = new TextureRegionDrawable(new TextureRegion(knob_tx));

        background_dr.setMinWidth(background_dr.getMinWidth()*scale);
        background_dr.setMinHeight(background_dr.getMinHeight()*scale);
        knob_dr.setMinWidth(knob_dr.getMinWidth()*scale);
        knob_dr.setMinHeight(knob_dr.getMinHeight()*scale);

        Slider.SliderStyle style = new Slider.SliderStyle();
        style.background = background_dr;
        style.knob = knob_dr;
        return style;
    }

    public CheckBox.CheckBoxStyle createCheckBox(float scale){
        Texture yes_tx= new Texture(Gdx.files.internal("yes_button.png"));
        Texture no_tx = new Texture(Gdx.files.internal("no_button.png"));

        Drawable yes_dr = new TextureRegionDrawable(new TextureRegion(yes_tx));
        Drawable no_dr = new TextureRegionDrawable(new TextureRegion(no_tx));

        yes_dr.setMinWidth(yes_dr.getMinWidth()*scale*0.75f);
        yes_dr.setMinHeight(yes_dr.getMinHeight()*scale*0.75f);
        no_dr.setMinWidth(no_dr.getMinWidth()*scale*0.75f);
        no_dr.setMinHeight(no_dr.getMinHeight()*scale*0.75f);

        CheckBox.CheckBoxStyle style = new CheckBox.CheckBoxStyle();
        style.checkboxOff = no_dr;
        style.checkboxOn = yes_dr;
        style.font = createFont(50);
        return style;
    }
}
