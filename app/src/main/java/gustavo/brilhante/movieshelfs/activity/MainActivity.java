package gustavo.brilhante.movieshelfs.activity;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import gustavo.brilhante.movieshelfs.R;
import gustavo.brilhante.movieshelfs.fragments.SearchFragment;
import gustavo.brilhante.movieshelfs.fragments.SearchFragment_;
import gustavo.brilhante.movieshelfs.fragments.ShelfFragment;
import gustavo.brilhante.movieshelfs.fragments.ShelfFragment_;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById
    LinearLayout shelfLayout, searchLayout;

    @ViewById
    View shelfLine, searchLine;

    @AfterViews
    void afterViews(){
        searchLayoutClick();
    }

    private void goTo(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment, "MENU").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    private void goToRightIn(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment, "MENU").setCustomAnimations(R.anim.anim_slide_out_left, R.anim.anim_slide_in_right).commit();

    }
    private void goToleftIn(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment, "MENU").setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right).commit();

    }

    @Click(R.id.shelfLayout)
    void shelfLayoutClick(){

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (!(fragment instanceof ShelfFragment)) {
            shelfLine.setVisibility(View.VISIBLE);
            searchLine.setVisibility(View.INVISIBLE);

            ShelfFragment_ shelfFragment = new ShelfFragment_();

            goToleftIn(shelfFragment);
        }

    }

    @Click(R.id.searchLayout)
    void searchLayoutClick(){

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (!(fragment instanceof SearchFragment)) {
            shelfLine.setVisibility(View.INVISIBLE);
            searchLine.setVisibility(View.VISIBLE);

            SearchFragment_ searchFragment = new SearchFragment_();

            goToRightIn(searchFragment);
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }


}
