package com.yuqi.acrossbeat;


import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;
import org.andengine.entity.modifier.SequenceEntityModifier;

import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import com.yuqi.base.BaseAnimatedSprite;
import com.yuqi.base.BaseBaseGameActivity;

public class MainSenceActivity_test extends BaseGameActivity implements IOnSceneTouchListener {

	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 405;
	// /** 屏幕宽度 */
	// private static int CAMERA_WIDTH = 0;
	// /** 屏幕高度 */
	// private static int CAMERA_HEIGHT = 0;

	private Camera mCamera;

	private Texture mTexture;
	private TiledTextureRegion mPlayerTextureRegion;
	private TiledTextureRegion mEnemyTextureRegion;

	private Texture mAutoParallaxBackgroundTexture;

	private TextureRegion mParallaxLayerBack;
	private TextureRegion mParallaxLayerMid;
	private TextureRegion mParallaxLayerFront;
	// 方向控制盘相关
	private Texture mOnScreenControlTexture;
	private TextureRegion mOnScreenControlBaseTextureRegion;
	private TextureRegion mOnScreenControlKnobTextureRegion;

	private TextureRegion mTextureRegion;

	// @Override
	// public Engine onLoadEngine() {
	// this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
	// return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE,
	// new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
	// }

//	@Override
//	public void onLoadResources() {
//		this.mTexture = new Texture(256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
//		this.mPlayerTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mTexture, this, "gfx/player.png", 0, 0, 3, 4);
//		this.mTextureRegion = TextureRegionFactory.createFromAsset(mAutoParallaxBackgroundTexture, this, "gfx/player.png", 0, 0);
//		this.mEnemyTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mTexture, this, "gfx/enemy.png", 150, 0, 3, 4);
//		// 1080,1920
//		this.mAutoParallaxBackgroundTexture = new Texture(1024, 1024, TextureOptions.DEFAULT);
//		this.mParallaxLayerFront = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "gfx/parallax_background_layer_front.png", 0, 0);
//		this.mParallaxLayerBack = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "gfx/parallax_background_layer_back.png", 0, 188);
//		this.mParallaxLayerMid = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "gfx/parallax_background_layer_mid.png", 0, 669);
//
//		this.mOnScreenControlTexture = new Texture(256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
//		this.mOnScreenControlBaseTextureRegion = TextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this, "gfx/onscreen_control_base.png", 0, 0);
//		this.mOnScreenControlKnobTextureRegion = TextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this, "gfx/onscreen_control_knob.png", 128, 0);
//
//		this.mEngine.getTextureManager().loadTextures(this.mTexture, this.mOnScreenControlTexture);
//
//		this.mEngine.getTextureManager().loadTextures(this.mTexture, this.mAutoParallaxBackgroundTexture);
//	}

	private Scene scene = new Scene(1);

	// @Override
	// public Scene onLoadScene() {
	// this.mEngine.registerUpdateHandler(new FPSLogger());
	//
	// final AutoParallaxBackground autoParallaxBackground = new
	// AutoParallaxBackground(0, 0, 0, 5);
	// autoParallaxBackground.addParallaxEntity(new ParallaxEntity(0.0f, new
	// Sprite(0, CAMERA_HEIGHT - this.mParallaxLayerBack.getHeight(),
	// this.mParallaxLayerBack)));
	// autoParallaxBackground.addParallaxEntity(new ParallaxEntity(-5.0f, new
	// Sprite(0, 80, this.mParallaxLayerMid)));
	// autoParallaxBackground.addParallaxEntity(new ParallaxEntity(-10.0f, new
	// Sprite(0, CAMERA_HEIGHT - this.mParallaxLayerFront.getHeight(),
	// this.mParallaxLayerFront)));
	// scene.setBackground(autoParallaxBackground);
	//
	// /*
	// * Calculate the coordinates for the face, so its centered on the
	// * camera.
	// */
	// final int playerX = (CAMERA_WIDTH -
	// this.mPlayerTextureRegion.getTileWidth()) / 2;
	// final int playerY = CAMERA_HEIGHT -
	// this.mPlayerTextureRegion.getTileHeight() - 5;
	//
	// /* Create two sprits and add it to the scene. */
	// final BaseAnimatedSprite player = new BaseAnimatedSprite(playerX,
	// playerY, this.mPlayerTextureRegion) {
	//
	// @Override
	// public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float
	// pTouchAreaLocalX, float pTouchAreaLocalY) {
	// Toast.makeText(getApplicationContext(), "player==onAreaTouched",
	// Toast.LENGTH_LONG).show();
	// return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
	// pTouchAreaLocalY);
	// }
	// };
	// player.setScaleCenterY(this.mPlayerTextureRegion.getTileHeight());
	// player.setScale(2);
	// player.animate(new long[] { 200, 200, 200 }, 3, 5, true);
	//
	// final BaseAnimatedSprite enemy = new BaseAnimatedSprite(playerX - 80,
	// playerY, this.mEnemyTextureRegion);
	// enemy.setScaleCenterY(this.mEnemyTextureRegion.getTileHeight());
	// enemy.setScale(2);
	// enemy.animate(new long[] { 200, 200, 200 }, 3, 5, true);
	// enemy.registerEntityModifier(pEntityModifier);
	// scene.getTopLayer().addEntity(player);
	//
	// scene.getTopLayer().addEntity(enemy);
	//
	//
	//
	//
	// final DigitalOnScreenControl digitalOnScreenControl = new
	// DigitalOnScreenControl((int) (CAMERA_WIDTH -
	// this.mOnScreenControlBaseTextureRegion.getWidth() * 1.25f), CAMERA_HEIGHT
	// - this.mOnScreenControlBaseTextureRegion.getHeight(), this.mCamera,
	// this.mOnScreenControlBaseTextureRegion,
	// this.mOnScreenControlKnobTextureRegion, 0.1f, new
	// IOnScreenControlListener() {
	// @Override
	// public void onControlChange(final BaseOnScreenControl
	// pBaseOnScreenControl, final float pValueX, final float pValueY) {
	// player.setPosition(player.getX() + pValueX * 10, player.getY() + pValueY
	// * 10);
	// }
	//
	// }) {
	// @Override
	// protected boolean onHandleControlBaseTouched(TouchEvent pSceneTouchEvent,
	// float pTouchAreaLocalX, float pTouchAreaLocalY) {
	// if (pSceneTouchEvent.getAction() == 1) {
	// player.setPosition(player.getX(), playerY);
	// }
	// Log.e("onHandleControlBaseTouched", "pSceneTouchEvent" +
	// pSceneTouchEvent.getAction());
	// return super.onHandleControlBaseTouched(pSceneTouchEvent,
	// pTouchAreaLocalX, pTouchAreaLocalY);
	// }
	//
	// };
	//
	// scene.setOnSceneTouchListener(this);
	//
	// digitalOnScreenControl.getControlBase().setBlendFunction(GL10.GL_SRC_ALPHA,
	// GL10.GL_ONE_MINUS_SRC_ALPHA);
	// digitalOnScreenControl.getControlBase().setAlpha(0.5f);
	// digitalOnScreenControl.getControlBase().setScaleCenter(0, 128);
	// digitalOnScreenControl.getControlBase().setScale(1.0f);
	// digitalOnScreenControl.getControlKnob().setScale(1.0f);
	//
	// digitalOnScreenControl.refreshControlKnobPosition();
	//
	// digitalOnScreenControl.setOnAreaTouchListener(new IOnAreaTouchListener()
	// {
	//
	// @Override
	// public boolean onAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea
	// pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY) {
	// Toast.makeText(getApplicationContext(),
	// "setOnAreaTouchListener==onAreaTouched", Toast.LENGTH_LONG).show();
	// return true;
	// }
	// });
	//
	// scene.registerTouchArea(player);
	// scene.registerTouchArea(digitalOnScreenControl.getControlKnob());
	// scene.setChildScene(digitalOnScreenControl);
	//
	// return scene;
	// }

//	@Override
//	public void onLoadComplete() {
//
//	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		Toast.makeText(getApplicationContext(), pScene.getClass().toString() + "==onSceneTouchEvent", Toast.LENGTH_LONG).show();
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.andengine.ui.IGameInterface#onCreateEngineOptions()
	 */
	@Override
	public EngineOptions onCreateEngineOptions() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.andengine.ui.IGameInterface#onCreateResources(org.andengine.ui.
	 * IGameInterface.OnCreateResourcesCallback)
	 */
	@Override
	public void onCreateResources(OnCreateResourcesCallback arg0) throws Exception {
//		this.mTexture = new Texture(256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
//		this.mPlayerTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mTexture, this, "gfx/player.png", 0, 0, 3, 4);
//		this.mTextureRegion = TextureRegionFactory.createFromSource(mAutoParallaxBackgroundTexture, this, "gfx/player.png", 0, 0);
//		this.mEnemyTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mTexture, this, "gfx/enemy.png", 150, 0, 3, 4);
//		// 1080,1920
//		this.mAutoParallaxBackgroundTexture = new Texture(1024, 1024, TextureOptions.DEFAULT);
//		this.mParallaxLayerFront = TextureRegionFactory.createFromSource(this.mAutoParallaxBackgroundTexture, this, "gfx/parallax_background_layer_front.png", 0, 0);
//		this.mParallaxLayerBack = TextureRegionFactory.createFromSource(this.mAutoParallaxBackgroundTexture, this, "gfx/parallax_background_layer_back.png", 0, 188);
//		this.mParallaxLayerMid = TextureRegionFactory.createFromSource(this.mAutoParallaxBackgroundTexture, this, "gfx/parallax_background_layer_mid.png", 0, 669);
//
//		this.mOnScreenControlTexture = new Texture(256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
//		this.mOnScreenControlBaseTextureRegion = TextureRegionFactory.createFromSource(this.mOnScreenControlTexture, this, "gfx/onscreen_control_base.png", 0, 0);
//		this.mOnScreenControlKnobTextureRegion = TextureRegionFactory.createFromSource(this.mOnScreenControlTexture, this, "gfx/onscreen_control_knob.png", 128, 0);
//
//		this.mEngine.getTextureManager().loadTextures(this.mTexture, this.mOnScreenControlTexture);
//
//		this.mEngine.getTextureManager().loadTextures(this.mTexture, this.mAutoParallaxBackgroundTexture);
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback arg0) throws Exception {
		this.mEngine.registerUpdateHandler(new FPSLogger());

//		final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
//		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0, CAMERA_HEIGHT - this.mParallaxLayerBack.getHeight(), this.mParallaxLayerBack, null)));
//		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-5.0f, new Sprite(0, 80, this.mParallaxLayerMid)));
//		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-10.0f, new Sprite(0, CAMERA_HEIGHT - this.mParallaxLayerFront.getHeight(), this.mParallaxLayerFront)));
//		scene.setBackground(autoParallaxBackground);

		/*
		 * Calculate the coordinates for the face, so its centered on the
		 * camera.
		 */
		final float playerX = (int)(CAMERA_WIDTH - this.mPlayerTextureRegion.getWidth()) / 2;
		final int playerY = (int)(CAMERA_HEIGHT - this.mPlayerTextureRegion.getHeight() - 5);

		/* Create two sprits and add it to the scene. */
		final BaseAnimatedSprite player = new BaseAnimatedSprite(playerX, playerY, playerY, playerY, this.mPlayerTextureRegion, null) {

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				Toast.makeText(getApplicationContext(), "player==onAreaTouched", Toast.LENGTH_LONG).show();
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
//		player.setScaleCenterY(this.mPlayerTextureRegion.getTileHeight());
		player.setScale(2);
		player.animate(new long[] { 200, 200, 200 }, 3, 5, true);

		final BaseAnimatedSprite enemy = new BaseAnimatedSprite(playerX - 80, playerY, playerY, playerY, this.mEnemyTextureRegion, null);
		enemy.setScaleCenterY(this.mEnemyTextureRegion.getHeight());
		enemy.setScale(2);
		enemy.animate(new long[] { 200, 200, 200 }, 3, 5, true);
		enemy.registerEntityModifier(null);
		scene.attachChild(player);

		scene.attachChild(enemy);

		// final DigitalOnScreenControl digitalOnScreenControl = new
		// DigitalOnScreenControl((int) (CAMERA_WIDTH -
		// this.mOnScreenControlBaseTextureRegion.getWidth() * 1.25f),
		// CAMERA_HEIGHT - this.mOnScreenControlBaseTextureRegion.getHeight(),
		// this.mCamera, this.mOnScreenControlBaseTextureRegion,
		// this.mOnScreenControlKnobTextureRegion, 0.1f, new
		// IOnScreenControlListener() {
		// @Override
		// public void onControlChange(final BaseOnScreenControl
		// pBaseOnScreenControl, final float pValueX, final float pValueY) {
		// player.setPosition(player.getX() + pValueX * 10, player.getY() +
		// pValueY * 10);
		// }
		//
		// }) {
		// @Override
		// protected boolean onHandleControlBaseTouched(TouchEvent
		// pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		// if (pSceneTouchEvent.getAction() == 1) {
		// player.setPosition(player.getX(), playerY);
		// }
		// Log.e("onHandleControlBaseTouched", "pSceneTouchEvent" +
		// pSceneTouchEvent.getAction());
		// return super.onHandleControlBaseTouched(pSceneTouchEvent,
		// pTouchAreaLocalX, pTouchAreaLocalY);
		// }
		//
		// };

		scene.setOnSceneTouchListener((IOnSceneTouchListener) this);

		scene.registerTouchArea(player);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.andengine.ui.IGameInterface#onPopulateScene(org.andengine.entity.
	 * scene.Scene, org.andengine.ui.IGameInterface.OnPopulateSceneCallback)
	 */
	@Override
	public void onPopulateScene(Scene arg0, OnPopulateSceneCallback arg1) throws Exception {

	}

}
