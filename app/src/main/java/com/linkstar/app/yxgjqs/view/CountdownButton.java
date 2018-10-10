
package com.linkstar.app.yxgjqs.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.Button;

@SuppressLint("AppCompatCustomView")
public class CountdownButton extends Button
{

	private final static int TIME = 60;
	private int second = 60;
	private boolean isRun = true;
	// 再次获取验证码的等待时间

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			if (msg.what == 1)
			{
				second--;
				CountdownButton.this.setText(second + "");
			}
			if (msg.what == 2)
			{
				second = TIME;
				CountdownButton.this.setText("获取验证码");
				setEnabled(true);
				isRun = true;
			}
		};
	};

	public CountdownButton(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);

		// setOnClickListener(this);
	}

	public CountdownButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public CountdownButton(Context context)
	{
		super(context);
	}

	@SuppressLint("ClickableViewAccessibility")
	public void startCountDown()
	{
		this.setEnabled(false);
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				while (isRun)
				{
					handler.sendEmptyMessage(1);
					try
					{
						Thread.sleep(1000);
						if (second == 0)
						{
							isRun = false;
							handler.sendEmptyMessage(2);
						}
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		})
		{

		}.start();
	}

	@Override
	public void setOnClickListener(OnClickListener l)
	{
		this.setClickable(false);
		super.setOnClickListener(l);
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		String s = null;
		setText(s = enabled ? "获取验证码" : "正在获取");

		super.setEnabled(enabled);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		VelocityTracker mVelocityTracker = VelocityTracker.obtain();
		mVelocityTracker.addMovement(event);

		return super.onTouchEvent(event);
	}

}
