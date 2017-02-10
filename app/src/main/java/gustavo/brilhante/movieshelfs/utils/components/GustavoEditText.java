package gustavo.brilhante.movieshelfs.utils.components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gustavo.brilhante.movieshelfs.R;

/**
 * Created by Gustavo on 03/02/17.
 */

public class GustavoEditText extends RelativeLayout {

    int inputType;
    String hintText;

    EditText editText;
    Button eraseButton;
    TextView hintTextView;

    LayoutInflater inflater = null;

    boolean editHasFocus = false;

    boolean isButtonChecked = false;

    boolean clickable = false;

    private static final int INPUT_TYPE_TEXT = 0;
    private static final int INPUT_TYPE_EMAIL = 1;
    private static final int INPUT_TYPE_NUMBER = 2;
    private static final int INPUT_TYPE_PASSWORD = 3;
    private static final int INPUT_TYPE_MAX_LENGTH = 4;

    public GustavoEditText(Context context) {
        super(context);
        initViews();
    }

    public GustavoEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GustavoEditText, 0, 0);
        try{
            hintText = typedArray.getString(R.styleable.GustavoEditText_floatingLabelText);
        }finally {
            if (!isInEditMode()) {
                initViews();
            }
        }

    }

    public GustavoEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void initViews() {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_gustavo_edittext, this, true);

        editText = (EditText) findViewById(R.id.editText);
        eraseButton = (Button) findViewById(R.id.eraseButton);
        hintTextView = (TextView) findViewById(R.id.hintTextView);

        hintTextView.setText(hintText);

        eraseButton.setAlpha(0f);

        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                editHasFocus = hasFocus;
                if(hasFocus){
                    clickable = true;
                    if(editText.getText().toString().isEmpty()){
                        hintTextView.animate().alpha(0f).setDuration(400).start();
                        //changeButtonAnimated(false);
                    }else{
                        if(isButtonChecked)changeButtonAnimated(false);
                    }
                }else{
                    clickable = false;
                    if(editText.getText().toString().isEmpty()){
                        hintTextView.animate().alpha(1f).setDuration(400).start();
                        //fadeButton(false);
                    }else{
                        if(!isButtonChecked)changeButtonAnimated(true);
                    }
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0)fadeButton(true);
                else fadeButton(false);
            }
        });

        eraseButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickable && editText.getText().toString().length()>0){
                    editText.setText("");
                }
            }
        });
    }

    void fadeButton(boolean fadein){
        if(fadein){
            eraseButton.animate().alpha(1f).setDuration(150).start();
        }else{
            eraseButton.animate().alpha(0f).setDuration(150).start();
        }
    }

    void changeButtonAnimated(boolean checked){
        if(checked) {
            eraseButton.animate().alpha(0f).setDuration(150).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    eraseButton.setBackground(getContext().getResources().getDrawable(R.drawable.checked));
                    eraseButton.animate().alpha(1f).setDuration(150).start();
                    isButtonChecked = true;
                }
            }).start();
        }else{
            eraseButton.animate().alpha(0f).setDuration(150).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    eraseButton.setBackground(getContext().getResources().getDrawable(R.drawable.erase));
                    eraseButton.animate().alpha(1f).setDuration(150).start();
                    isButtonChecked = false;
                }
            }).start();
        }
    }

    public EditText getEditText(){
        return editText;
    }

    public String getText(){
        return editText.getText().toString();
    }


}
