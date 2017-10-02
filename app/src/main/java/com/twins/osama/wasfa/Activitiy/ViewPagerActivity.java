package com.twins.osama.wasfa.Activitiy;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.twins.osama.wasfa.Adapters.SliderAdapter;
import com.twins.osama.wasfa.Classes.Recipe;
import com.twins.osama.wasfa.R;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.twins.osama.wasfa.Helpar.Const.FAVRIT_POSSITION;
import static com.twins.osama.wasfa.Helpar.Const.IF_FROM_FAVRIT;
import static com.twins.osama.wasfa.Helpar.Const.INDEX;
import static com.twins.osama.wasfa.Helpar.Const.MENU_CID;
import static com.twins.osama.wasfa.Helpar.Const.RECIP_LIST;

public class ViewPagerActivity extends FragmentActivity {
    private SliderAdapter adapter;
    private ViewPager mPager;
    private int post;
    private ArrayList<Recipe> RecipList = new ArrayList<>();
    private CheckBox checkbox;
    private int inde;
    boolean isSeleact = false;
    private Realm realm;
    private ImageView go_back;
    private ArrayList<Recipe> list = new ArrayList<>();
    private Recipe resultRecipe;
    private boolean ifFromFavarit;
    private Recipe recipe;
    private int favritPossetion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        Realm.init(getApplication());
        realm = Realm.getDefaultInstance();

        post = getIntent().getIntExtra(MENU_CID, 0);
        inde = getIntent().getIntExtra(INDEX, 0);
        RecipList = getIntent().getParcelableArrayListExtra(RECIP_LIST);
        ifFromFavarit = getIntent().getBooleanExtra(IF_FROM_FAVRIT, false);
        favritPossetion = getIntent().getIntExtra(FAVRIT_POSSITION, 0);

        go_back = (ImageView) findViewById(R.id.go_back_fa);
        mPager = (ViewPager) findViewById(R.id.viewpager);
        checkbox = (CheckBox) findViewById(R.id.checkbox1);

        final RealmResults<Recipe> result = realm.where(Recipe.class).findAll();
        if (ifFromFavarit) {
            list = (ArrayList<Recipe>) realm.copyFromRealm(result);
            adapter = new SliderAdapter(getSupportFragmentManager(), list);
            adapter.notifyDataSetChanged();
            mPager.setAdapter(adapter);
            mPager.setCurrentItem(favritPossetion);
            mPager.setOffscreenPageLimit(0);
        } else {
            adapter = new SliderAdapter(getSupportFragmentManager(), RecipList);
            adapter.notifyDataSetChanged();
            mPager.setAdapter(adapter);
            mPager.setCurrentItem(inde);
            mPager.setOffscreenPageLimit(0);
        }
        if (ifFromFavarit) {

            recipe = list.get(favritPossetion);
        } else {
            recipe = RecipList.get(inde);
        }
        if (!(result.isEmpty())) {
            resultRecipe = realm.where(Recipe.class)
                    .equalTo("cid", Integer.parseInt(recipe.getCid() + ""))
                    .equalTo("wid", Integer.parseInt(recipe.getWid() + "")).findFirst();
            if (resultRecipe != null) {
                recipe.setSeleact(true);
                checkbox.setChecked(true);
            } else {
                recipe.setSeleact(false);
                checkbox.setChecked(false);
            }
        }
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (ifFromFavarit) {
                    recipe = list.get(position);
                } else {
                    recipe = RecipList.get(position);
                }
                if (!(result.isEmpty())) {
                    resultRecipe = realm.where(Recipe.class)
                            .equalTo("cid", Integer.parseInt(recipe.getCid() + ""))
                            .equalTo("wid", Integer.parseInt(recipe.getWid() + "")).findFirst();
                    if (resultRecipe != null) {
                        recipe.setSeleact(true);
                        checkbox.setChecked(true);
                    } else {
                        recipe.setSeleact(false);
                        checkbox.setChecked(false);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             finish();
            }
        });
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox chk = (CheckBox) view; // important line and code work
                if (chk.isChecked()) {
                    if (ifFromFavarit) {
                        isSeleact = true;
                        Toast.makeText(getApplication(), getString(R.string.saveItem), Toast.LENGTH_LONG).show();
                        realm.beginTransaction();
                        realm.copyToRealm(list.get(mPager.getCurrentItem()));
                        realm.commitTransaction();
                    } else {
                        isSeleact = true;
                        Toast.makeText(getApplication(), getString(R.string.saveItem), Toast.LENGTH_LONG).show();
                        realm.beginTransaction();
                        realm.copyToRealm(RecipList.get(mPager.getCurrentItem()));
                        realm.commitTransaction();
                    }
                } else {
                    if (ifFromFavarit) {
                        isSeleact = false;
                        resultRecipe = realm.where(Recipe.class)
                                .equalTo("wid", Integer.parseInt(list
                                        .get(mPager.getCurrentItem()).getWid() + "")).findFirst();
                        if (resultRecipe != null) {
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    Recipe rows = realm.where(Recipe.class).equalTo("wid", Integer.parseInt(list
                                            .get(mPager.getCurrentItem()).getWid() + "")).findFirst();
                                    if (rows != null) {
                                        rows.deleteFromRealm();
                                        Toast.makeText(getApplication(), getString(R.string.deleteItem), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else
                            Toast.makeText(getApplication(), "From list favarit", Toast.LENGTH_LONG).show();
                    } else {
                        isSeleact = false;
                        resultRecipe = realm.where(Recipe.class)
                                .equalTo("cid", Integer.parseInt(RecipList.get(mPager.getCurrentItem())
                                        .getCid() + "")).equalTo("wid", Integer.parseInt(RecipList
                                        .get(mPager.getCurrentItem()).getWid() + "")).findFirst();
                        if (resultRecipe != null) {
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    Recipe rows = realm.where(Recipe.class).equalTo("cid", post)
                                            .equalTo("wid", Integer.parseInt(RecipList
                                                    .get(mPager.getCurrentItem()).getWid() + "")).findFirst();
                                    if (rows != null) {
                                        rows.deleteFromRealm();
                                        Toast.makeText(getApplication(), getString(R.string.deleteItem), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else
                            Toast.makeText(getApplication(), "From RecipList", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
finish();

    }
}
