package vn.dongtrieu.astrochannels.utils;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import vn.dongtrieu.astrochannels.base.Const;
import vn.dongtrieu.astrochannels.models.Channel;
import vn.dongtrieu.astrochannels.models.User;

import static vn.dongtrieu.astrochannels.base.Const.FIREBASE_KEY_CHANNELS;
import static vn.dongtrieu.astrochannels.base.Const.FIREBASE_KEY_USERS;

public class FirebaseUtils {
    private static FirebaseUtils mInstance;

    private FirebaseDatabase mFirebaseDatabase;

    private FirebaseUtils () {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
    }

    public static void initInstance () {
        if (mInstance == null) {
            mInstance = new FirebaseUtils();
        }
    }

    public static FirebaseUtils getInstance() {
        return mInstance;
    }

    public void saveNewUser (String userId, String email) {
        if (!TextUtils.isEmpty(userId) && !TextUtils.isEmpty(email)) {
            mFirebaseDatabase.getReference(FIREBASE_KEY_USERS).child(userId).setValue(new User(userId,email));
        }
    }

    public String getUser(final String userId) {
        final TaskCompletionSource<String> tcs = new TaskCompletionSource<>();

        mFirebaseDatabase
                .getReference(FIREBASE_KEY_USERS)
                .child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        tcs.setResult(user != null ? user.email : null);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        tcs.setException(new IOException(databaseError.getMessage(), databaseError.toException()));
                    }
                });

        Task<String> task = tcs.getTask();

        try {
            Tasks.await(task);
        } catch (ExecutionException | InterruptedException e) {
            task = Tasks.forException(e);
        }

        String email = null;
        if (task.isSuccessful()) {
            email = task.getResult();
        }

        return email;
    }

    public void saveFavouriteChannel(Channel channel) {
        if (channel != null && FirebaseAuth.getInstance().getCurrentUser() != null) {
            mFirebaseDatabase
                    .getReference(FIREBASE_KEY_CHANNELS)
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(String.valueOf(channel.getChannelId()))
                    .setValue(channel);
        }
    }

    public Channel[] loadFavouriteChannels() {
        final TaskCompletionSource<Channel[]> tcs = new TaskCompletionSource<>();

        mFirebaseDatabase
                .getReference(FIREBASE_KEY_CHANNELS)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Channel> listChannel = new ArrayList<>();
                        for (DataSnapshot channelSnapshot : dataSnapshot.getChildren()) {
                            listChannel.add(channelSnapshot.getValue(Channel.class));
                        }
                        tcs.setResult(listChannel.toArray(new Channel[0]));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        tcs.setException(new IOException(databaseError.getMessage(), databaseError.toException()));
                    }
                });

        Task<Channel[]> task = tcs.getTask();

        try {
            Tasks.await(task);
        } catch (ExecutionException | InterruptedException e) {
            task = Tasks.forException(e);
        }

        Channel[] email = null;
        if (task.isSuccessful()) {
            email = task.getResult();
        }

        return email;
    }

    public void removeFavouriteChannel(Channel channel) {
        if (channel != null && FirebaseAuth.getInstance().getCurrentUser() != null) {
            mFirebaseDatabase
                    .getReference(FIREBASE_KEY_CHANNELS)
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(String.valueOf(channel.getChannelId()))
                    .removeValue();
        }
    }
}
