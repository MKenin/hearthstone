/*
 *  This file is part of the Haven & Hearth game client.
 *  Copyright (C) 2009 Fredrik Tolf <fredrik@dolda2000.com>, and
 *                     Björn Johannessen <johannessen.bjorn@gmail.com>
 *
 *  Redistribution and/or modification of this file is subject to the
 *  terms of the GNU Lesser General Public License, version 3, as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  Other parts of this source tree adhere to other copying
 *  rights. Please see the file `COPYING' in the root directory of the
 *  source tree for details.
 *
 *  A copy the GNU Lesser General Public License is distributed along
 *  with the source tree of which this file is a part in the file
 *  `doc/LPGL-3'. If it is missing for any reason, please see the Free
 *  Software Foundation's website at <http://www.fsf.org/>, or write
 *  to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *  Boston, MA 02111-1307 USA
 */

package haven.render;

import haven.Disposable;
import haven.FColor;

public class Texture implements Disposable {
    public final VectorFormat ifmt, efmt;
    public final DataBuffer.Usage usage;
    public final DataBuffer.Filler<? super Image> init;
    public Disposable ro;

    public Texture(DataBuffer.Usage usage, VectorFormat ifmt, VectorFormat efmt, DataBuffer.Filler<? super Image> init) {
	this.usage = usage;
	this.ifmt = ifmt;
	this.efmt = efmt;
	this.init = init;
    }

    public static class Image<T extends Texture> implements DataBuffer {
	public final T tex;
	public final int w, h, d;
	public final int level;

	public Image(T tex, int w, int h, int d, int level) {
	    this.tex = tex;
	    this.w = w;
	    this.h = h;
	    this.d = d;
	    this.level = level;
	}

	public int size() {
	    return(w * h * d * tex.efmt.size());
	}
    }

    public void dispose() {
	synchronized(this) {
	    if(ro != null) {
		ro.dispose();
		ro = null;
	    }
	}
    }

    public enum Filter {
	NEAREST, LINEAR,
    }

    public enum Wrapping {
	REPEAT, REPEAT_MIRROR,
	CLAMP, CLAMP_BORDER, CLAMP_MIRROR,
    }

    public abstract static class Sampler<T extends Texture> implements Disposable {
	public final T tex;
	public Filter magfilter = Filter.LINEAR, minfilter = Filter.NEAREST, mipfilter = Filter.LINEAR;
	public Wrapping swrap = Wrapping.REPEAT, twrap = Wrapping.REPEAT, rwrap = Wrapping.REPEAT;
	public FColor border = FColor.BLACK;
	public Disposable ro;

	public Sampler(T tex) {
	    this.tex = tex;
	}

	public void dispose() {
	    synchronized(this) {
		if(ro != null) {
		    ro.dispose();
		    ro = null;
		}
	    }
	}

	public Sampler<T> magfilter(Filter v) {magfilter = v; return(this);}
	public Sampler<T> minfilter(Filter v) {minfilter = v; return(this);}
	public Sampler<T> mipfilter(Filter v) {mipfilter = v; return(this);}
	public Sampler<T> swrap(Wrapping v) {swrap = v; return(this);}
	public Sampler<T> twrap(Wrapping v) {twrap = v; return(this);}
	public Sampler<T> rwrap(Wrapping v) {rwrap = v; return(this);}
	public Sampler<T> wrapmode(Wrapping v) {return(swrap(v).twrap(v).rwrap(v));}
	public Sampler<T> border(FColor v) {border = v; return(this);}
    }
}
