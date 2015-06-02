package com.refocus.puneet.axishackathon.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.refocus.puneet.axishackathon.R;

public class TutorialActivity extends ActionBarActivity implements ViewPagerEx.OnPageChangeListener
{

    TextView welcomeText;
    LinearLayout ll;
    FrameLayout buttonLAyout;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        welcomeText = (TextView) findViewById(R.id.welcometext);
        ll = (LinearLayout) findViewById(R.id.linearLayout2);
        buttonLAyout = (FrameLayout) findViewById(R.id.buttonframe);
        mButton = (Button) findViewById(R.id.button);
        SliderLayout sliderShow = (SliderLayout) findViewById(R.id.image_header);
        TextSliderView textSliderView1 = new TextSliderView(this);
        TextSliderView textSliderView2 = new TextSliderView(this);
        TextSliderView textSliderView3 = new TextSliderView(this);
        TextSliderView textSliderView4 = new TextSliderView(this);
        TextSliderView textSliderView5 = new TextSliderView(this);
        // initialize a SliderLayout
        textSliderView1
                .image(R.drawable.tut1)
                .setScaleType(BaseSliderView.ScaleType.Fit);
        sliderShow.addSlider(textSliderView1);

        textSliderView2
                .image(R.drawable.tut2)
                .setScaleType(BaseSliderView.ScaleType.Fit);
        sliderShow.addSlider(textSliderView2);

        textSliderView3
                .image(R.drawable.tut3)
                .setScaleType(BaseSliderView.ScaleType.Fit);
        sliderShow.addSlider(textSliderView3);

        textSliderView4
                .image(R.drawable.tut4)
                .setScaleType(BaseSliderView.ScaleType.Fit);
        sliderShow.addSlider(textSliderView4);

        textSliderView5
                .image(R.drawable.tut5)
                .setScaleType(BaseSliderView.ScaleType.Fit);
        sliderShow.addSlider(textSliderView5);

        sliderShow.setPresetTransformer(SliderLayout.Transformer.Default);
        //sliderShow.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));
        sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderShow.addOnPageChangeListener(this);
        sliderShow.stopAutoCycle();

        buttonLAyout.setVisibility(View.INVISIBLE);
        mButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent SearchIntent = new Intent(TutorialActivity.this, LoginActivity.class);
                SearchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                SearchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(SearchIntent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tutorial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int i, float v, int i2)
    {

    }

    @Override
    public void onPageSelected(int i)
    {
        if (i == 4)
        {
            welcomeText.setVisibility(View.INVISIBLE);
            ll.setVisibility(View.INVISIBLE);
            buttonLAyout.setVisibility(View.VISIBLE);
        }
        else
        {
            buttonLAyout.setVisibility(View.INVISIBLE);
            welcomeText.setVisibility(View.VISIBLE);
            ll.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i)
    {

    }
}
