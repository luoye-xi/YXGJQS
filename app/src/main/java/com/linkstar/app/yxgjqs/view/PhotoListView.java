package com.linkstar.app.yxgjqs.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.linkstar.app.yxgjqs.R;

public class PhotoListView extends LinearLayout
{
	private OnAddorDelectPhotoListener mOnAddorDelectPhotoListener;
	
	public PhotoListView(Context context)
	{
		super(context);
	}

	public PhotoListView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}

	public PhotoListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	
	private int getPhotoNum(){
		return this.getChildCount();
	}
	
	public void addPhoto(Bitmap bitmap){
		final CanDisappearImageView mCanDisappearImageView = new CanDisappearImageView(getContext());
		mCanDisappearImageView.setImageBitmap(bitmap);
		mCanDisappearImageView.setOnDeleteClickListener(delteListener);
		this.addView(mCanDisappearImageView);
		if(null != mOnAddorDelectPhotoListener)
			mOnAddorDelectPhotoListener.onAdd(getPhotoNum());
	}
	
	public int getPhotoCount() {
		return this.getChildCount();
	}
	
	public Bitmap[] getPhotos() {
		Bitmap[] bitmaps = new Bitmap[getChildCount()];
		for (int i = 0; i <getChildCount(); i++)
		{
			CanDisappearImageView civ = (CanDisappearImageView) this.getChildAt(i);
			bitmaps[i] = civ.getBitmap(); 
		}
		return bitmaps;
	}
	
	
	private OnDeleteClickListener delteListener = new OnDeleteClickListener(){

		@Override
		public void onDeleteClick(View v)
		{
			removeView(v);
			if(null != mOnAddorDelectPhotoListener)
				mOnAddorDelectPhotoListener.onDelete(getPhotoNum());
		}

		
		
	};
	 
	public void setOnDelectPhotoListener(OnAddorDelectPhotoListener onDelectPhotoListener){
		mOnAddorDelectPhotoListener = onDelectPhotoListener;
	}
	
	public static interface OnAddorDelectPhotoListener{
		void onAdd(int num);
		void onDelete(int num);
	}
	
	interface OnDeleteClickListener{
		void onDeleteClick(View v);
	}
	
	class CanDisappearImageView extends LinearLayout{
		private ImageView ivShow,ivDelete;
		private Bitmap showBitmap;
		private OnDeleteClickListener delteListener;
		
		
		public CanDisappearImageView(Context context)
		{
			super(context);
			initView();
		}

		public CanDisappearImageView(Context context, AttributeSet attrs)
		{
			super(context, attrs);
			initView();
		}
		
		

		private void initView()
		{
			View view = inflate(getContext(), R.layout.can_disappear_imageview, this);
			ivShow =view.findViewById(R.id.iv_canDisappear_show);
			ivDelete = view.findViewById(R.id.iv_canDisappear_delete);
		}
		
		
		public void setOnDeleteClickListener(OnDeleteClickListener deleteClickListener)
		{
			delteListener = deleteClickListener;
			ivDelete.setOnClickListener(onDelteClick);
		}
		
		OnClickListener onDelteClick = new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				delteListener.onDeleteClick(CanDisappearImageView.this);
			}
		};
		
		public void setImageBitmap(Bitmap mBitmap)
		{
			this.showBitmap = mBitmap;
			ivShow.setImageBitmap(mBitmap);
		}
		
		public Bitmap getBitmap()
		{
			return showBitmap;
		}
	}

}
