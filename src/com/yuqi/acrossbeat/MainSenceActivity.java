/**
 * 
 */
package com.yuqi.acrossbeat;

import org.andengine.opengl.texture.Texture;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.AccelerationParticleInitializer;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ColorParticleModifier;
import org.andengine.entity.particle.modifier.ExpireParticleInitializer;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.modifier.ease.EaseQuadIn;
import org.andengine.util.modifier.ease.EaseQuadOut;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.yuqi.common.Constant;
import com.yuqi.common.MenuID;
import com.yuqi.menu.ExitDialogMenu;

import android.hardware.SensorManager;
import android.opengl.GLES20;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/**
 * @author HaoR
 * 
 * @date 2015-8-6 下午7:59:29
 * 
 */
public class MainSenceActivity extends SimpleBaseGameActivity implements IOnSceneTouchListener,IOnMenuItemClickListener {
	// ===========================================================
	// Constants
	// ===========================================================
	private static final String TAG = "MainSenceActivity";

	// ===========================================================
	// Fields
	// ===========================================================

	public Camera mCamera;
	private BitmapTextureAtlas mBitmapTextureAtlas;
	private TiledTextureRegion mPlayerTextureRegion;
	private TiledTextureRegion mEnemyTextureRegion;
	/** 背景 */
	private BitmapTextureAtlas mAutoParallaxBackgroundTexture;
	private ITextureRegion mParallaxLayerBack;
	private ITextureRegion mParallaxLayerMid;
	private ITextureRegion mParallaxLayerFront;
	/** 播放、暂停 */
	private BitmapTextureAtlas mPlayPauseBackgroundTexture;
	private ITextureRegion mPlayPauseTextureRegion;

	private ITextureRegion mParticleTextureRegion;
	private BitmapTextureAtlas mOnScreenControlTexture;
	private ITextureRegion mOnScreenControlBaseTextureRegion;
	private ITextureRegion mOnScreenControlKnobTextureRegion;

	private SpriteParticleSystem particleSystem;
	/** 退出对话框 */
	private ExitDialogMenu mExitDialogMenu=null;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		mCamera = new Camera(0, 0, Constant.CAMERA_WIDTH, Constant.CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(Constant.CAMERA_WIDTH, Constant.CAMERA_HEIGHT), mCamera);
	}

	@Override
	public void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		this.mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "player.png", 0, 0, 3, 4);
		this.mEnemyTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "enemy.png", 73, 0, 3, 4);
		this.mBitmapTextureAtlas.load();
		//背景相关
		this.mAutoParallaxBackgroundTexture = new BitmapTextureAtlas(this.getTextureManager(), 1024, 1024);
		this.mParallaxLayerFront = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "parallax_background_layer_front.png", 0, 0);
		this.mParallaxLayerBack = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "parallax_background_layer_back.png", 0, 188);
		this.mParallaxLayerMid = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "parallax_background_layer_mid.png", 0, 669);
		this.mAutoParallaxBackgroundTexture.load();
		//播放、暂停 
		this.mPlayPauseBackgroundTexture = new BitmapTextureAtlas(this.getTextureManager(), 512, 512);
		this.mPlayPauseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mPlayPauseBackgroundTexture, this, "main_sence_pause.png", 0, 0);
		this.mPlayPauseBackgroundTexture.load();
		
		this.mOnScreenControlTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.BILINEAR);
		this.mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this, "onscreen_control_base.png", 0, 0);
		this.mOnScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this, "onscreen_control_knob.png", 128, 0);
		this.mOnScreenControlTexture.load();
		
		// 离子系统相关代码
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mParticleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "particle_fire.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(this.mBitmapTextureAtlas);

		//退出对话框相关
		mExitDialogMenu=new ExitDialogMenu(MainSenceActivity.this);
		mExitDialogMenu.menuCreateResource();
		mExitDialogMenu.createMenuScene();
		mExitDialogMenu.getMenuScene().setOnMenuItemClickListener(this);
	}

	private boolean isUp = false;

	private Scene scene = new Scene();

	private AnalogOnScreenControl velocityOnScreenControl = null;

	@Override
	public Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 10);
		final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0, Constant.CAMERA_HEIGHT - this.mParallaxLayerBack.getHeight(), this.mParallaxLayerBack, vertexBufferObjectManager)));
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-5.0f, new Sprite(0, 80, this.mParallaxLayerMid, vertexBufferObjectManager)));
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-10.0f, new Sprite(0, Constant.CAMERA_HEIGHT - this.mParallaxLayerFront.getHeight(), this.mParallaxLayerFront, vertexBufferObjectManager)));
		scene.setBackground(autoParallaxBackground);

		final float playerX = (Constant.CAMERA_WIDTH - this.mPlayerTextureRegion.getWidth()) / 2;
		final float playerY = Constant.CAMERA_HEIGHT - this.mPlayerTextureRegion.getHeight() - 5;

		/* Create two sprits and add it to the scene. */
		final AnimatedSprite player = new AnimatedSprite(playerX, playerY, this.mPlayerTextureRegion, vertexBufferObjectManager);
		player.setScaleCenterY(this.mPlayerTextureRegion.getHeight());
		player.setScale(2);
		player.animate(new long[] { 100, 100, 100 }, 3, 5, true);

		final Sprite mPlayPauseMenu = new Sprite(playerX, playerY, this.mPlayPauseTextureRegion, vertexBufferObjectManager) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				scene.setChildScene(mExitDialogMenu.getMenuScene(),false,false,false);
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};

		mPlayPauseMenu.setWidth(40);
		mPlayPauseMenu.setHeight(40);
		mPlayPauseMenu.setPosition(Constant.CAMERA_WIDTH - mPlayPauseMenu.getWidth() - 10, 10);
		mPlayPauseMenu.setVisible(true);

		particleSystem = new SpriteParticleSystem(new PointParticleEmitter(0, 400), 6, 10, 200, mParticleTextureRegion, this.getVertexBufferObjectManager());

		final AnimatedSprite enemy = new AnimatedSprite(playerX - 80, playerY, this.mEnemyTextureRegion, vertexBufferObjectManager) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		enemy.setScaleCenterY(this.mEnemyTextureRegion.getHeight());
		enemy.setScale(2);
		enemy.animate(new long[] { 100, 100, 100 }, 3, 5, true);

		scene.attachChild(player);
		scene.attachChild(enemy);
		scene.attachChild(mPlayPauseMenu);
		scene.registerTouchArea(enemy);
		scene.registerTouchArea(mPlayPauseMenu);

		final float x1 = 25;
		final float y1 = Constant.CAMERA_HEIGHT - this.mOnScreenControlBaseTextureRegion.getHeight();
		velocityOnScreenControl = new AnalogOnScreenControl(x1, y1, this.mCamera, this.mOnScreenControlBaseTextureRegion, this.mOnScreenControlKnobTextureRegion, 0.1f, this.getVertexBufferObjectManager(), new IAnalogOnScreenControlListener() {
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
				if (pValueX != 0 || pValueY != 0) {
					Log.i(TAG, "pValueX==" + pValueX);
					Log.i(TAG, "pValueY==" + pValueY);
				}

				float MIN_MOST_MOVE_UP = 0.5f;
				float MIN_MOST_MOVE_DOWN = -0.5f;

				if (pValueX > MIN_MOST_MOVE_UP && pValueY > MIN_MOST_MOVE_UP) {
					Log.i(TAG, "right down");
				}
				if (pValueX > MIN_MOST_MOVE_UP && pValueY < MIN_MOST_MOVE_DOWN) {
					Log.i(TAG, "right up");
				}
				if (pValueX < MIN_MOST_MOVE_DOWN && pValueY > MIN_MOST_MOVE_UP) {
					Log.i(TAG, "left down");
				}
				if (pValueX < MIN_MOST_MOVE_DOWN && pValueY < MIN_MOST_MOVE_DOWN) {
					Log.i(TAG, "left up");
				}

				if (Math.abs(pValueX) <= MIN_MOST_MOVE_UP && pValueY > MIN_MOST_MOVE_UP) {
					Log.i(TAG, "down");
				}
				if (Math.abs(pValueX) <= MIN_MOST_MOVE_UP && pValueY < MIN_MOST_MOVE_DOWN) {
					Log.i(TAG, "up");
					float orgX = player.getX();
					float orgY = player.getY();
					float durationTime = 0.5f;
					float moveY = 100;
					float moveX = 0;
					player.registerEntityModifier(new SequenceEntityModifier(new ParallelEntityModifier(new MoveXModifier(durationTime, orgX, orgX + moveX), new MoveYModifier(durationTime, orgY, orgY - moveY, EaseQuadOut.getInstance())), new ParallelEntityModifier(new MoveXModifier(durationTime, orgX + moveX, orgX + 2 * moveX), new MoveYModifier(durationTime, orgY - moveY, playerY, EaseQuadIn.getInstance()))));
				}
				if (pValueX < MIN_MOST_MOVE_DOWN && Math.abs(pValueY) <= MIN_MOST_MOVE_UP) {
					Log.i(TAG, "left");
				}
				if (pValueX > MIN_MOST_MOVE_UP && Math.abs(pValueY) <= MIN_MOST_MOVE_UP) {
					Log.i(TAG, "right");
				}

			}

			@Override
			public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {
				/* Nothing. */

			}
		}) {
			@Override
			protected boolean onHandleControlBaseTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
				// if (isUp) {
				// // player.setPosition(player.getX(), playerY);
				// // 玩家跳跃
				// float orgX = player.getX();
				// float orgY = player.getY();
				// float durationTime = 0.5f;
				// float moveY = 100;
				// float moveX = 0;
				// player.registerEntityModifier(new SequenceEntityModifier(new
				// ParallelEntityModifier(new MoveXModifier(durationTime, orgX,
				// orgX + moveX), new MoveYModifier(durationTime, orgY, orgY -
				// moveY, EaseQuadOut.getInstance())), new
				// ParallelEntityModifier(new MoveXModifier(durationTime, orgX +
				// moveX, orgX + 2 * moveX), new MoveYModifier(durationTime,
				// orgY - moveY, playerY, EaseQuadIn.getInstance()))));
				// isUp = false;
				// }
				// } else if (pSceneTouchEvent.getAction() ==
				// TouchEvent.ACTION_MOVE) {
				// player.setPosition(player.getX(), playerY);
				// }

				return super.onHandleControlBaseTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		velocityOnScreenControl.getControlBase().setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		velocityOnScreenControl.getControlBase().setAlpha(0.5f);

		scene.setChildScene(velocityOnScreenControl);

		PhysicsWorld mPhysicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH), false);
		final Rectangle ground = new Rectangle(0, Constant.CAMERA_HEIGHT - 2, Constant.CAMERA_WIDTH, 2, vertexBufferObjectManager);
		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f);
		PhysicsFactory.createBoxBody(mPhysicsWorld, ground, BodyType.StaticBody, wallFixtureDef);
		scene.attachChild(ground);

		// 离子系统相关
		particleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
		particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(15, 22, -60, -90));
		particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Sprite>(5, 15));
		particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 360.0f));
		particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(1.0f, 0.0f, 0.0f));
		particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(11.5f));

		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 5, 0.5f, 2.0f));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(2.5f, 3.5f, 1.0f, 0.0f));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(3.5f, 4.5f, 0.0f, 1.0f));
		particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(0.0f, 11.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(4.5f, 11.5f, 1.0f, 0.0f));

		scene.attachChild(particleSystem);

		return scene;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		Log.i(TAG, "pScene==" + pScene + "==pSceneTouchEvent==" + pSceneTouchEvent);
		return true;
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
		switch(pMenuItem.getID()){
		case MenuID.EXIT_DIALOG_OK:
			scene.setChildScene(velocityOnScreenControl);
			break;
		case MenuID.EXIT_DIALOG_CANCEL:
			finish();
			break;
		}
		return false;
	}
}
