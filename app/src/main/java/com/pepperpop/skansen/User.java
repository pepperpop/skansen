package com.pepperpop.skansen;

import com.google.firebase.auth.FirebaseUser;

public class User {

  private final FirebaseUser mFirebaseUser;

  public User(FirebaseUser firebaseUser) {
    this.mFirebaseUser = firebaseUser;
  }

  public String getDisplayName() {
    return mFirebaseUser.getDisplayName();
  }

  public String getEmail() {
    return mFirebaseUser.getEmail();
  }
}
