package com.copypasteit.istanbul.SliderFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.copypasteit.istanbul.R;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;


public class SliderFragment extends Fragment {


    ImageSlider imageSlider;
    public SliderFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View View = inflater.inflate(R.layout.layout_slider, container, false);

        List<SlideModel> imageList = new ArrayList<>();
        imageSlider = View.findViewById(R.id.imege_slider);


        imageList.add(new SlideModel("https://apps.bncodeing.com/public/frontend/monako_slider/1679165722228735.jpg", "Aya Sofya of Istanbul."));
        imageList.add(new SlideModel("https://apps.bncodeing.com/public/frontend/monako_slider/1679165758093549.jpg", "Blue Mosque (Sultan Ahmet Camii)."));
        imageList.add(new SlideModel("https://apps.bncodeing.com/public/frontend/monako_slider/1679166267867340.jpg", "Üsküdar in Istanbul"));
        imageList.add(new SlideModel("https://apps.bncodeing.com/public/frontend/monako_slider/1679165808269911.jpg", "Hippodrome in Istanbul"));
        imageList.add(new SlideModel("https://apps.bncodeing.com/public/frontend/monako_slider/1679166287903864.jpg", "Pera Museum, Tepebasi"));
        imageList.add(new SlideModel("https://apps.bncodeing.com/public/frontend/monako_slider/1679165839482504.jpg", "Little Aya Sofya (Küçük Aya Sofya)"));
        imageList.add(new SlideModel("https://apps.bncodeing.com/public/frontend/monako_slider/1679165823831234.jpg", "Istanbul Archaeology Museum"));


        imageSlider.setImageList(imageList, true);

        return View;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



}
