package com.example.android.android_me;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.android_me.data.AndroidImageAssets;
import com.example.android.android_me.ui.AndroidMeActivity;

public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener {

    private int headIndex;
    private int bodyIndex;
    private int legIndex;
    private boolean Towpane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(findViewById(R.id.android_me_linear_layout) != null){
            Towpane = true;
            Button button = (Button)findViewById(R.id.next_Btn);
            button.setVisibility(View.GONE);
            GridView gridView = (GridView)findViewById(R.id.images_grid_view);
            gridView.setNumColumns(2);
            if(savedInstanceState == null) {
                BodyPart BodyPartFragment = new BodyPart();
                BodyPartFragment.setmImageIds(AndroidImageAssets.getHeads());
                BodyPartFragment.setmListIndex(1);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.head_container, BodyPartFragment)
                        .commit();

                BodyPartFragment.setmImageIds(AndroidImageAssets.getBodies());
                BodyPartFragment.setmListIndex(1);
                fragmentManager.beginTransaction()
                        .add(R.id.body_container, BodyPartFragment)
                        .commit();

                BodyPartFragment.setmImageIds(AndroidImageAssets.getLegs());
                BodyPartFragment.setmListIndex(1);
                fragmentManager.beginTransaction()
                        .add(R.id.leg_container, BodyPartFragment)
                        .commit();
            }
        }else{
            Towpane = false;
        }
    }

    @Override
    public void onImageSelected(int position) {
        Toast.makeText(this,"Position Clicked = "+position,Toast.LENGTH_SHORT).show();
        int bodyPartNumber = position/12;
        int listIndex = position - 12*bodyPartNumber;
        if(Towpane){
            BodyPart bodyPart = new BodyPart();
            switch (bodyPartNumber){
                case 0:
                    bodyPart.setmImageIds(AndroidImageAssets.getHeads());
                    bodyPart.setmListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.head_container,bodyPart)
                            .commit();
                    break;
                case 1:
                    bodyPart.setmImageIds(AndroidImageAssets.getBodies());
                    bodyPart.setmListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.body_container,bodyPart)
                            .addToBackStack(null)
                            .commit();
                    break;
                case 2:
                    bodyPart.setmImageIds(AndroidImageAssets.getLegs());
                    bodyPart.setmListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.leg_container,bodyPart)
                            .commit();
                    break;
                default:break;
            }
        }else{
            switch (bodyPartNumber) {
                case 0:
                    headIndex = listIndex;
                    break;
                case 1:
                    bodyIndex = listIndex;
                    break;
                case 2:
                    legIndex = listIndex;
                    break;
                default:
                    break;
            }
        }
        Bundle b = new Bundle();
        b.putInt("headIndex",headIndex);
        b.putInt("bodyIndex",bodyIndex);
        b.putInt("legIndex",legIndex);

        final Intent intent = new Intent(this, AndroidMeActivity.class);
        intent.putExtras(b);

        Button nextBtn = (Button)findViewById(R.id.next_Btn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

    }
}

