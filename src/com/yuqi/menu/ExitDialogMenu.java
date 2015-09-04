/**
 * 
 */
package com.yuqi.menu;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import android.opengl.GLES20;
import com.yuqi.acrossbeat.MainSenceActivity;
import com.yuqi.common.Constant;
import com.yuqi.common.MenuID;

/**
 * @author HaoR
 * 
 * @date 2015-9-4 下午5:49:32
 * 
 */
public class ExitDialogMenu {
	private MainSenceActivity mMainSenceActivity = null;

	/** 菜单相关 */
	private BitmapTextureAtlas mMenuTexture;
	public MenuScene mMenuScene;
	/** “标题” */
	protected ITextureRegion mTitleTextureRegion;
	/** “确定”按钮 */
	protected ITextureRegion mOKTextureRegion;
	/** “取消”按钮 */
	protected ITextureRegion mCancelTextureRegion;

	public ExitDialogMenu(MainSenceActivity mMainSenceActivity) {
		this.mMainSenceActivity = mMainSenceActivity;
	}

	/** 在onCreateResources中调用 */
	public void menuCreateResource() {
		// 播放、暂停菜单相关
		mMenuTexture = new BitmapTextureAtlas(mMainSenceActivity.getTextureManager(), 512, 256, TextureOptions.BILINEAR);
		mOKTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mMenuTexture, mMainSenceActivity, "startbutton.png", 0, 0);
		mCancelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mMenuTexture, mMainSenceActivity, "scoresbutton.png", 0, 50);
		mTitleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mMenuTexture, mMainSenceActivity, "title480.png", 0, 100);
		mMenuTexture.load();

		mMenuScene = new MenuScene(mMainSenceActivity.mCamera);
	}

	/** 返回MenuScene */
	public MenuScene getMenuScene() {
		return mMenuScene;
	}

	private static final float STRECH_RATE=1.5f;
	private static final float TITLE_WIDTH=184f*STRECH_RATE;
	private static final float TITLE_HEIGHT=72f*STRECH_RATE;
	
	private static final float OK_CANCEL_WIDTH=64f*STRECH_RATE;
	private static final float OK_CANCEL_HEIGHT=24f*STRECH_RATE;
	
	
	public void createMenuScene() {
		final SpriteMenuItem mTitleMenuItem = new SpriteMenuItem(MenuID.EXIT_DIALOG_TITLE, mTitleTextureRegion, mMainSenceActivity.getVertexBufferObjectManager());
		mTitleMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		mTitleMenuItem.setWidth(TITLE_WIDTH);
		mTitleMenuItem.setHeight(TITLE_HEIGHT);
		mTitleMenuItem.setPosition((Constant.CAMERA_WIDTH - mTitleMenuItem.getWidth()) / 2, (Constant.CAMERA_HEIGHT-TITLE_HEIGHT-OK_CANCEL_HEIGHT)/2);
		mMenuScene.addMenuItem(mTitleMenuItem);

		final SpriteMenuItem mOKMenuItem = new SpriteMenuItem(MenuID.EXIT_DIALOG_OK, mOKTextureRegion, mMainSenceActivity.getVertexBufferObjectManager());
		mOKMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		mOKMenuItem.setWidth(OK_CANCEL_WIDTH);
		mOKMenuItem.setHeight(OK_CANCEL_HEIGHT);
		mOKMenuItem.setPosition(Constant.CAMERA_WIDTH / 2 - mOKMenuItem.getWidth(),(Constant.CAMERA_HEIGHT+TITLE_HEIGHT-OK_CANCEL_HEIGHT)/2);
		mMenuScene.addMenuItem(mOKMenuItem);

		final SpriteMenuItem mCancelMenuItem = new SpriteMenuItem(MenuID.EXIT_DIALOG_CANCEL, mCancelTextureRegion, mMainSenceActivity.getVertexBufferObjectManager());
		mCancelMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		mCancelMenuItem.setWidth(OK_CANCEL_WIDTH);
		mCancelMenuItem.setHeight(OK_CANCEL_HEIGHT);
		mCancelMenuItem.setPosition(Constant.CAMERA_WIDTH / 2, (Constant.CAMERA_HEIGHT+TITLE_HEIGHT-OK_CANCEL_HEIGHT)/2);
		mMenuScene.addMenuItem(mCancelMenuItem);

		// mMenuScene.buildAnimations();
		mMenuScene.setBackgroundEnabled(false);

	}

}
