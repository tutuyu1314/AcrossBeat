/**
 * 
 */
package com.yuqi.base;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.vbo.ITiledSpriteVertexBufferObject;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

/**
 * @author HaoR
 * 
 * @date 2015-7-15 ÏÂÎç8:39:13
 * 
 */
public class BaseAnimatedSprite extends AnimatedSprite {

	/**
	 * @param pX
	 * @param pY
	 * @param pWidth
	 * @param pHeight
	 * @param pTiledTextureRegion
	 * @param pTiledSpriteVertexBufferObject
	 */
	public BaseAnimatedSprite(float pX, float pY, float pWidth, float pHeight, ITiledTextureRegion pTiledTextureRegion, ITiledSpriteVertexBufferObject pTiledSpriteVertexBufferObject) {
		super(pX, pY, pWidth, pHeight, pTiledTextureRegion, pTiledSpriteVertexBufferObject);
	}

	/**
	 * @param pX
	 * @param pY
	 * @param pTiledTextureRegion
	 */

}
