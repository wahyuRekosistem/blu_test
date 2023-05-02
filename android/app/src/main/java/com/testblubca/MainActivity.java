package com.testblubca;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blusdk.BluSdk;
import com.blusdk.log.BluLogType;
import com.blusdk.webview.BluWebView;
import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint;
import com.facebook.react.defaults.DefaultReactActivityDelegate;
import com.testblubca.databinding.BluViewBinding;

public class MainActivity extends ReactActivity implements BluWebView.IBluWebViewListener {

  private BluViewBinding binding;

  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  @Override
  protected String getMainComponentName() {
    return "TestBluBCA";
  }

  /**
   * Returns the instance of the {@link ReactActivityDelegate}. Here we use a util class {@link
   * DefaultReactActivityDelegate} which allows you to easily enable Fabric and Concurrent React
   * (aka React 18) with two boolean flags.
   */
  @Override
  protected ReactActivityDelegate createReactActivityDelegate() {
    return new DefaultReactActivityDelegate(
        this,
        getMainComponentName(),
        // If you opted-in for the New Architecture, we enable the Fabric Renderer.
        DefaultNewArchitectureEntryPoint.getFabricEnabled(), // fabricEnabled
        // If you opted-in for the New Architecture, we enable Concurrent React (i.e. React 18).
        DefaultNewArchitectureEntryPoint.getConcurrentReactEnabled() // concurrentRootEnabled
        );
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
//    LayoutInflater a = LayoutInflater.from(getApplicationContext());
//    a.inflate(R.layout.blu_view, null);
    binding = BluViewBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    BluSdk.INSTANCE.initialize(this, BluSdk.ENV_DEV, "app-blu-rekosistem-dev");
    binding.bluView.setWebViewListener(this);
    binding.bluView.setContextParent(this);
  }

  @Override
  public void onBackPressed() {
    if (binding.bluView.hasHandleBackPress()) {
      binding.bluView.onBackPressed();
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    binding.bluView.handleActivityResult(requestCode, resultCode, data);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    binding.bluView.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  @Override
  public void onDestroy() {
    BluSdk.INSTANCE.destroy();
    super.onDestroy();
  }

  @Override
  public void bluLog(@NonNull BluLogType bluLogType, @Nullable String s, @Nullable Throwable throwable) {
    System.out.print("BluSdk bluLogType " + bluLogType);
    System.out.print("BluSdk s " + s);
    System.out.print("BluSdk throwable " + throwable);
  }

  @Override
  public void bluFailedLoadWebView(@Nullable Throwable throwable) {

  }

  @Override
  public void bluGenerateSignatureFailed() {
    Log.d("BluSdk", "generate signature failed");
  }

  @Override
  public void bluIsNeedLinkage() {
    Log.d("BluSdk", "need linkage");
  }

  @Override
  public void bluIsNeedReloadPartialView() {
    Log.d("BluSdk", "need to reload partial view");
  }

  @Override
  public void bluWebViewDidFinishNavigation() {

  }

  @Override
  public void bluWebViewDidStartNavigation() {
    Log.d("BluSdk", "start nav");
  }

  @Override
  public void bluWebViewLoading(int i) {
    Log.d("BluSdk", "loading webview $progress");
  }

  @Override
  public void onClose() {

  }

  @Override
  public void onLinkageFailed() {
    Log.d("BluSdk", "linkage failed");
  }

  @Override
  public void onPaymentCompleted() {

  }

  @Override
  public void onQrisCompleted() {

  }
}
