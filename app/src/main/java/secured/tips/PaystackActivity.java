package secured.tips;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class PaystackActivity extends AppCompatActivity {

    private static final int GROUP_LEN = 4;

    //final CardSingleton si = CardSingleton.getInstance();
    EditText mEditCardNum;
    EditText mEditCVC;
    EditText mEditExpiryMonth;
    EditText mEditExpiryYear;
    //Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paystack);
    }
}
