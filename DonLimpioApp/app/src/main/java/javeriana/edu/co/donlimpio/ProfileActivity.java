package javeriana.edu.co.donlimpio;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import javeriana.edu.co.classes.User;

public class ProfileActivity extends AppCompatActivity {

    private String TAG = "EditProfile";
    public static final int IMAGE_REQUEST = 100;
    public static final int CAMERA_REQUEST = 20;
    private StorageReference mStorageRef, imageRef;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    CircleImageView circlePhoto;
    //ImageButton circlePhoto;
    Uri imageUri, downloadPhoto;
    InputStream imageStream;
    UploadTask uploadTask;
    Button ownS;
    View profileLayout;
    String userID, photoURL;
    TextView fullname, useremail, userphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileLayout = findViewById(R.id.profile_layout);
        fullname = findViewById(R.id.name_TextView);
        useremail = findViewById(R.id.email_TextView);
        userphone = findViewById(R.id.phone_TextView);

        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://donlimpioapp.appspot.com/Users/" + userID + "/profilePhoto");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        imageRef = FirebaseStorage.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Toast.makeText(getBaseContext(), "Successfully signed with " + user.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "User has signed out", Toast.LENGTH_SHORT).show();
                }
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    User u = new User();
                    u.setFirstName(ds.child(userID).getValue(User.class).getFirstName());
                    u.setLastName(ds.child(userID).getValue(User.class).getLastName());
                    u.setEmail(ds.child(userID).getValue(User.class).getEmail());
                    u.setUserPhoneNumber(ds.child(userID).getValue(User.class).getUserPhoneNumber());

                    fullname.setText(u.getFirstName() + " " + u.getLastName());
                    useremail.setText(u.getEmail());
                    userphone.setText(Long.toString(u.getUserPhoneNumber()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        photoURL = mStorageRef.child("Users/").child(userID).child("profilePhoto").getDownloadUrl().toString();
        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(photoURL).into(circlePhoto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getBaseContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        ownS = (Button) findViewById(R.id.own_services);
        circlePhoto = (CircleImageView) findViewById(R.id.profile_image);
        //circlePhoto = (ImageButton) findViewById(R.id.profile_image);

        ownS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),OwnServicesActivity.class);
                startActivity(i);
            }
        });

        boolean hasPermissionGallery = (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermissionGallery)
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},IMAGE_REQUEST);
        else {
            circlePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    choosePhoto();
                }
            });
        }

        boolean hasPermissionCamera = (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermissionCamera) {
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.CAMERA},CAMERA_REQUEST);
        } else {
            circlePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    choosePhoto();
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null)
            mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case IMAGE_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(profileLayout, "¡Permiso concedido!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(profileLayout, "¡La aplicación no puede usar la galeria!",
                            Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
            case CAMERA_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(profileLayout, "¡Permiso concedido!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(profileLayout, "¡La aplicación no puede usar la cámara!",
                            Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        }
    }

    private void choosePhoto() {
        final CharSequence[] options = {"Camara", "Galeria", "Cancelar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Cambiar Foto");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (options[i].equals("Camara")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "fotoPerfil.jpg");
                    imageUri = Uri.fromFile(f);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CAMERA_REQUEST);
                } else if (options[i].equals("Galeria")) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, IMAGE_REQUEST);
                } else if (options[i].equals("Cancelar")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CAMERA_REQUEST: {
                if (resultCode == RESULT_OK) {
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    BitmapFactory.decodeFile(imageUri.getPath()).compress(Bitmap.CompressFormat.JPEG, 80, bytes);
                    circlePhoto.setImageBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray())));
                    uploadFireBase();
                }
            }
            case IMAGE_REQUEST: {
                if (resultCode == RESULT_OK) {
                    try{
                        imageUri = data.getData();
                        circlePhoto.setImageBitmap(BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri)));
                        uploadFireBase();
                    }catch (Exception e){
                        Log.d(TAG, e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void uploadFireBase(){

        if (imageUri != null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = mStorageRef.child("Users/").child(userID).child("profilePhoto");
            ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(getBaseContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getBaseContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                            .getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                }
            });
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
}
