package com.bbgo.common_base.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActivityChooserModel;
import f.b.a.i.a;
import f.b.c.b;
import i.a0.d.l;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0014J\b\u0010\u0011\u001a\u00020\u000eH\u0014J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/bbgo/common_base/base/BaseActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "resId", "", "(I)V", "TAG", "", "getTAG", "()Ljava/lang/String;", ActivityChooserModel.ATTRIBUTE_ACTIVITY, "Landroid/app/Activity;", "activityWR", "Ljava/lang/ref/WeakReference;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onOptionsItemSelected", "", "item", "Landroid/view/MenuItem;", "common-base_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes.dex */
public class BaseActivity extends AppCompatActivity {
    @Nullable

    /* renamed from: e  reason: collision with root package name */
    public Activity f27e;
    @Nullable

    /* renamed from: f  reason: collision with root package name */
    public WeakReference<Activity> f28f;

    public BaseActivity(int i2) {
        super(i2);
        l.d(getClass().getSimpleName(), "this.javaClass.simpleName");
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        b.a().c(this);
        this.f27e = this;
        Activity activity = this.f27e;
        l.c(activity);
        WeakReference<Activity> weakReference = new WeakReference<>(activity);
        this.f28f = weakReference;
        a.a.a(weakReference);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.f27e = null;
        a.a.b(this.f28f);
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(@NotNull MenuItem menuItem) {
        l.e(menuItem, "item");
        if (menuItem.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}