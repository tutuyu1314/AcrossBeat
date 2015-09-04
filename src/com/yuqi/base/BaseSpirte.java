/**
 * 
 */
package com.yuqi.base;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;


/**
 * @author HaoR
 * 
 * @date 2015-7-15 ÏÂÎç8:35:10
 * 
 */
public class BaseSpirte extends Sprite {

	/**
	 * @param pX
	 * @param pY
	 * @param pWidth
	 * @param pHeight
	 * @param pTextureRegion
	 * @param pSpriteVertexBufferObject
	 */
	public BaseSpirte(float pX, float pY, float pWidth, float pHeight, ITextureRegion pTextureRegion, ISpriteVertexBufferObject pSpriteVertexBufferObject) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pSpriteVertexBufferObject);
	}


}
