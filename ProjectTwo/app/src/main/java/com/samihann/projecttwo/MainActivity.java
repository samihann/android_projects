package com.samihann.projecttwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/********************************************************
 * By,
 * Samihan Nandedkar
 *
 * CS 478: Project Two
 * Spring 2022
 *
 * Main activity when application is opened             *
 *******************************************************/


public class MainActivity extends AppCompatActivity {

    // declare the gridview
    GridView gridview;

    // declare & assign the values with the array for the grid view.
    String[] names = {"Cat","Leopard","Chimpanzee","Tiger","Jellyfish","Eagle","Fox","Highland Cattle"};
    int[] images = {R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4,R.drawable.image5,R.drawable.image6,R.drawable.image7,R.drawable.image8};
    String[] url_list = { "https://en.wikipedia.org/wiki/Cat",
            "https://en.wikipedia.org/wiki/Leopard",
            "https://en.wikipedia.org/wiki/Chimpanzee",
            "https://en.wikipedia.org/wiki/Tiger",
            "https://en.wikipedia.org/wiki/Jellyfish",
            "https://en.wikipedia.org/wiki/Eagle",
            "https://en.wikipedia.org/wiki/Fox",
            "https://en.wikipedia.org/wiki/Highland_cattle"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for the activity
        setContentView(R.layout.activity_main);

        // Initialize the gridview and register it to context menu for long click.
        gridview = findViewById(R.id.gridView);
        registerForContextMenu(gridview);

        // Create a custom adapter for the gridview & setting the adapter for grid view.
        CustomAdapter customAdapter = new CustomAdapter(names, images, this);
        gridview.setAdapter(customAdapter);

        // Set on click listener for grid item.
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedName = names[i];
                int selectedImage = images[i];

                // Start the second activity when grid item is clicked.
                startActivity(new Intent(MainActivity.this,AnimalImageViewActivity.class).putExtra("name",selectedName).putExtra("image",selectedImage));

            }
        });

    }

    // Defining a custom adapter for the grid view.
    public class CustomAdapter extends BaseAdapter{
        // Declaring private variables.
        private String[] imageNames;
        private int[] imagesPhoto;
        private Context context;
        private LayoutInflater layoutInflater;

        // Defininig the custom adapter function.
        public CustomAdapter(String[] imageNames, int[] imagesPhoto, Context context) {
            this.imageNames = imageNames;
            this.imagesPhoto = imagesPhoto;
            this.context = context;
            this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        // Overriding the get count method of base adapter.
        @Override
        public int getCount() {
            return imagesPhoto.length;
        }

        // Overriding the get item method of base adapter.
        @Override
        public Object getItem(int i) {
            return null;
        }

        // Overriding the get getItemId method of base adapter.
        @Override
        public long getItemId(int i) {
            return 0;
        }


        // Overriding the get View method of base adapter.
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            // Adding the layout inflator
            if(view == null){
                view = layoutInflater.inflate(R.layout.grid_items, viewGroup, false);

            }
            // Setting the values of textView and ImageView for each item of a grid.
            TextView itemTextView = view.findViewById(R.id.tvName);
            ImageView itemImageView = view.findViewById(R.id.imageView);

            itemTextView.setText(imageNames[i]);
            itemImageView.setImageResource(imagesPhoto[i]);

            // Returning the view.
            return view;
        }
    }

    // Creating the context menu.
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.gridView) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
        }
    }

    // Defining the action when context menu item is selected.
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int i = info.position;
        String selectedName = names[i];
        int selectedImage = images[i];
        switch(item.getItemId()) {
            // When option one is clicked
            case R.id.option_1:
                startActivity(new Intent(MainActivity.this,AnimalImageViewActivity.class).putExtra("name",selectedName).putExtra("image",selectedImage));
                return true;
            // When option two is clicked
            case R.id.option_2:
                startActivity(new Intent(MainActivity.this,AnimalDetailsActivity.class).putExtra("name",selectedName).putExtra("image",selectedImage));
                return true;
            // When option three is clicked.
            case R.id.option_3:
                // Create explicit intent.
                String url = url_list[i];
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}