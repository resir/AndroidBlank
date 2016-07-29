package com.itguai.blank.widget;//package com.tqmall.art2000.widget;
//
//import android.content.Context;
//import android.graphics.*;
//import android.graphics.drawable.Drawable;
//import android.util.AttributeSet;
//import android.view.*;
//import android.view.GestureDetector.OnGestureListener;
//import android.view.View.OnTouchListener;
//import com.jianbaba.android.widget.GifView.ImageChangedListener;
//
///**
// * @author: ChengJianyun
// * 2012-3-5 11:52:03
// */
//public class ImageControl extends SurfaceView implements OnTouchListener, SurfaceHolder.Callback {
//
//    public float trueScale = 1;
//    private MultiPoint prevPoint = new MultiPoint();
//    private MultiPoint lastPoint = new MultiPoint();
//    private MultiPoint translate = new MultiPoint();
//    private int canvasWidth;
//    private int canvasHeight;
//    private int action = -1;
//    private float base = 0, scale = 1;
//    private float oldScale = 1;
//    private boolean dragMode = true;
//    private PointF preScale = new PointF();
//    private PointF prevMid = new PointF();
//    private PointF lastMid = new PointF();
//    private SurfaceHolder holder;
//    private DrawItem di = new DrawItem();
//    private MyAnimation ani;
//    private DrawThread drawThread;
//    private Object curDrawLock = new Object();
//    private GifView gif;
//    private OnTapEvent onTapEventListenner;
//
//    public void reset() {
//        trueScale = 1;
//        base = 0;
//        scale = 1;
//        oldScale = 1;
//    }
//
//    public GifView getGif() {
//        return gif;
//    }
//
//    public ImageControl(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init();
//    }
//
//    public ImageControl(Context context) {
//        super(context);
//        init();
//    }
//
//    public ImageControl(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        init();
//    }
//
//    public interface OnTapEvent {
//
//        public void onSingleTapUp();
//
//
//        public void onDoubleTap();
//
//        public void onDoubleFingerTouch();
//    }
//
//    private void init() {
//        isInEditMode();
//        holder = getHolder();
//        holder.addCallback(this);
//        setOnTouchListener(this);
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        // if (null != di.getBitmap() && false) {
//        // //����ӦСŪview
//        // // heightMeasureSpec = (int) (UIMApp.getIns().dm.widthPixels
//        // // / di.srcWidth * di.srcHeight);
//        // // setMeasuredDimension(UIMApp.getIns().dm.widthPixels,
//        // // heightMeasureSpec);
//        // //ȫ��
//        // setMeasuredDimension(UIMApp.getIns().dm.widthPixels,
//        // UIMApp.getIns().dm.heightPixels);
//        // } else {
//        // setMeasuredDimension(0, 0);
//        // }
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }
//
//    public void setImage(Bitmap bitmap) {
//        di.setBitmap(bitmap);
//    }
//
//    public void setGif(GifView gif) {
//        this.gif = gif;
//        di.setBitmap(gif.nextBitmap());
//        gif.setImageChangedListener(new ImageChangedListener() {
//            @Override
//            public boolean imageChanged(Bitmap bitmap) {
//                di.setBitmap(bitmap);
//                drawThread.redraw();
//                return true;
//            }
//        });
//    }
//
//
//    private void upEvent() {
//        translate.x1 = translate.x2;
//        translate.y1 = translate.y2;
//        lastPoint.clear();
//        prevPoint.clear();
//        base = 0;
//    }
//
//
//    private MultiPoint getPosition(MotionEvent event, MultiPoint p) {
//        if (p == null) {
//            p = new MultiPoint();
//        }
//        p.clear();
//        p.x1 = event.getX();
//        p.y1 = event.getY();
//        p.points = 1;
//        if (event.getPointerCount() >= 2) {
//            int id = event.findPointerIndex(1);
//            if (id > -1) {
//                p.x2 = event.getX(id);
//                p.y2 = event.getY(id);
//                p.points = 2;
//            }
//        }
//        return p;
//    }
//
//
//    private void edge() {
//        if (di.width > canvasWidth) {
//            if (translate.x2 < canvasWidth - di.width) {
//                ani = new MyAnimation(translate, MyAnimation.MODE_X, translate.x2, canvasWidth - di.width);
//            } else if (translate.x2 > 0) {
//                ani = new MyAnimation(translate, MyAnimation.MODE_X, translate.x2, 0);
//            }
//        }
//        if (di.height >= canvasHeight) {
//            if (translate.y2 < canvasHeight - di.height) {
//                if (ani == null) {
//                    ani = new MyAnimation(translate, MyAnimation.MODE_Y, translate.y2, canvasHeight - di.height);
//                } else {
//                    ani.addY(translate.y2, canvasHeight - di.height);
//                }
//            } else if (translate.y2 > 0) {
//                if (ani == null) {
//                    ani = new MyAnimation(translate, MyAnimation.MODE_Y, translate.y2, 0);
//                } else {
//                    ani.addY(translate.y2, 0);
//                }
//            }
//        }
//    }
//
//    public void setOnTapEventListenner(OnTapEvent listenner) {
//        onTapEventListenner = listenner;
//    }
//
//    GestureDetector gd = new GestureDetector(new OnGestureListener() {
//
//        @Override
//        public boolean onSingleTapUp(MotionEvent e) {
//            if (null != onTapEventListenner) {
//                onTapEventListenner.onSingleTapUp();
//            }
//            return false;
//        }
//
//        @Override
//        public void onShowPress(MotionEvent e) {
//        }
//
//        @Override
//        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            return false;
//        }
//
//        @Override
//        public void onLongPress(MotionEvent e) {
//        }
//
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            return false;
//        }
//
//        @Override
//        public boolean onDown(MotionEvent e) {
//            return false;
//        }
//    });
//
//    public void setSacle(float scale) {
//        oldScale = scale;
//        trueScale = scale;
//        di.setScale(scale);
//        reDraw();
//    }
//
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        gd.onTouchEvent(event);
//        switch (event.getAction() & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_DOWN:
//                if (null != ani) {
//                    ani.cancle();
//                }
//                prevPoint = getPosition(event, prevPoint);
//                lastPoint = getPosition(event, lastPoint);
//                action = 0;
//                if (null != gif) {
//                    gif.stop();
//                }
//                break;
//            case MotionEvent.ACTION_POINTER_DOWN:
//                // ��¼�е�
//                break;
//            case MotionEvent.ACTION_MOVE:
//                lastPoint = getPosition(event, lastPoint);
//                if (lastPoint.points >= 2) {
//                    if (null != onTapEventListenner) {
//                        onTapEventListenner.onDoubleFingerTouch();
//                    }
//                    dragMode = false;
//                    float length = (float) Math.sqrt(Math.pow(lastPoint.x1 - lastPoint.x2, 2)
//                            + Math.pow(lastPoint.y1 - lastPoint.y2, 2));
//
//                    if (base == 0) {
//                        // ��¼�е�
//                        prevMid.x = (lastPoint.x1 + lastPoint.x2) / 2;
//                        prevMid.y = (lastPoint.y1 + lastPoint.y2) / 2;
//                        base = length;
//                        scale = 1;
//
//                        trueScale = scale * oldScale;
//                        float initScale = Math.min(canvasWidth / di.srcWidth, canvasHeight / di.srcHeight);
//                        di.setScale(initScale * trueScale);
//                        // ��¼ԭʼ����
//
//                        translate.x1 = translate.x2;
//                        translate.y1 = translate.y2;
//                        preScale.x = (prevMid.x - translate.x2) / di.width;
//                        preScale.y = (prevMid.y - translate.y2) / di.height;
//                        return true;
//                    } else {
//                        scale = length / base;
//                        trueScale = scale * oldScale;
//                        float initScale = Math.min(canvasWidth / di.srcWidth, canvasHeight / di.srcHeight);
//                        di.setScale(initScale * trueScale);
//                        // ����λ��
//                        lastMid.x = (lastPoint.x1 + lastPoint.x2) / 2;
//                        lastMid.y = (lastPoint.y1 + lastPoint.y2) / 2;
//                        translate.x2 = translate.x1 + lastMid.x - prevMid.x
//                                + ((prevMid.x - translate.x1) / di.width - preScale.x) * di.width;
//                        translate.y2 = translate.y1 + lastMid.y - prevMid.y
//                                + ((prevMid.y - translate.y1) / di.height - preScale.y) * di.height;
//                    }
//                } else if (dragMode) {
//                    if (trueScale == 1) {
//                        return true;
//                    }
//                    translate.x2 = translate.x1 + lastPoint.x1 - prevPoint.x1;
//                    translate.y2 = translate.y1 + lastPoint.y1 - prevPoint.y1;
//                }
//                break;
//            case MotionEvent.ACTION_CANCEL:
//            case MotionEvent.ACTION_UP:
//                action = -1;
//                dragMode = true;
//                try {
//                    Thread.sleep(50);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                upEvent();
//                ani = null;
//                if (trueScale < 1) {// ����z
//                    ani = new MyAnimation(ImageControl.this, MyAnimation.MODE_SCALE, trueScale, 1);
//                    oldScale = 1;
//                } else {
//                    trueScale = oldScale;
//                }
//                edge();
//                if (null != ani) {
//                    ani.start(drawThread);
//                }
//                if (null != gif) {
//                    gif.start();
//                }
//                break;
//
//            case MotionEvent.ACTION_POINTER_2_UP:
//            case MotionEvent.ACTION_POINTER_1_UP:
//                if (event.getAction() == MotionEvent.ACTION_POINTER_2_UP) {
//                    action = 2;
//                } else if (event.getAction() == MotionEvent.ACTION_POINTER_1_UP) {
//                    action = 1;
//                }
//                upEvent();
//                oldScale = trueScale;
//                try {
//                    Thread.sleep(50);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                prevPoint = getPosition(event, prevPoint);
//                break;
//        }
//        reDraw();
//        return true;
//
//    }
//
//    public void reDraw() {
//        if (null != drawThread) {
//            drawThread.redraw();
//        }
//    }
//
//    @Override
//    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//        canvasWidth = width;
//        canvasHeight = height;
//        if (null != di) {
//            edge();
//            drawThread.redraw();
//        }
//    }
//
//    @Override
//    public void surfaceCreated(SurfaceHolder holder) {
//        if (null == drawThread) {
//            drawThread = new DrawThread();
//            drawThread.start();
//        } else {
//            drawThread.redraw();
//        }
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder holder) {
//        // if(di.getBitmap()!=null && !di.getBitmap().isRecycled()){
//        // di.getBitmap().recycle();
//        // }
//    }
//
//    public class DrawThread extends Thread {
//        Paint paint = new Paint();
//        Matrix matrix = new Matrix();
//
//        public DrawThread() {
//            //			paint.setAntiAlias(true);
//            //			paint.setDither(true);
//            paint.setFilterBitmap(true);
//        }
//
//        public void redraw() {
//            //			synchronized (this) {
//            //				this.notify();
//            //			}
//        }
//
//        @Override
//        public void run() {
//            while (true) {
//                prepare();
//                draw();
//                //				try {
//                //					synchronized (this) {
//                //						this.wait();
//                //					}
//                //				} catch (InterruptedException e) {
//                //
//                //				}
//            }
//        }
//
//
//        private void draw() {
//            try {
//                Canvas canvas = holder.lockCanvas(null);
//                try {
//                    canvas.drawARGB(255, 0, 0, 0);
//                    matrix.reset();
//                    matrix.postScale(di.getScale(), di.getScale());//
//                    matrix.postTranslate(di.translateX, di.translateY);
//                    canvas.drawBitmap(di.getBitmap(), matrix, paint);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    holder.unlockCanvasAndPost(canvas);
//                }
//            } catch (Exception e) {
//
//            }
//        }
//
//
//        private void prepare() {
//            // ��ʼ ���ű���
//            float initScale = Math.min(canvasWidth / di.srcWidth, canvasHeight / di.srcHeight);
//            // �������Ŵ�С
//            di.setScale(initScale * trueScale);
//            // ����λ��
//            int tt = 0;
//            if (di.width > canvasWidth) {
//                di.translateX = translate.x2;
//            } else {
//                tt++;
//                di.translateX = (canvasWidth - di.width) / 2;
//            }
//            if (di.height > canvasHeight) {
//                di.translateY = translate.y2;
//            } else {
//                tt++;
//                di.translateY = (canvasHeight - di.height) / 2;
//            }
//            if (tt == 2) {
//                translate.clear();
//            }
//        }
//    }
//}
//
//class MyAnimation {
//    public static final int MODE_X = 1;
//    public static final int MODE_Y = 2;
//    public static final int MODE_XY = 3;
//    public static final int MODE_SCALE = 4;
//    public static final int MODE_XYS = 7;
//    private boolean isCancle = false;
//    private ImageControl imageControl;
//    private MultiPoint translate;
//    private int mode = 0;
//    private float xfrom, xto, yfrom, yto, sfrom, sto;
//
//    public MyAnimation(MultiPoint translate, int mode, float from, float to) {
//        init(mode, from, to);
//        this.translate = translate;
//    }
//
//
//    public void cancle() {
//        isCancle = true;
//    }
//
//    public MyAnimation(ImageControl ic, int mode, float from, float to) {
//        imageControl = ic;
//        init(mode, from, to);
//    }
//
//    private void init(int mode, float from, float to) {
//        this.mode |= mode;
//        this.sfrom = this.xfrom = this.yfrom = from;
//        this.sto = this.xto = this.yto = to;
//    }
//
//
//    public void addX(float from, float to) {
//        this.mode |= MODE_X;
//        this.xfrom = from;
//        this.xto = to;
//    }
//
//
//    public void addY(float from, float to) {
//        this.mode |= MODE_Y;
//        this.yfrom = from;
//        this.yto = to;
//    }
//
//    private void let(float value, int mode) {
//        switch (mode) {
//            case MODE_X:
//                translate.x1 = value;
//                translate.x2 = translate.x1;
//                break;
//            case MODE_Y:
//                translate.y1 = value;
//                translate.y2 = translate.y1;
//                break;
//            case MODE_SCALE:
//                imageControl.trueScale = value;
//                break;
//        }
//    }
//
//    private void mlet(float xcur, float ycur, float scale) {
//        if ((mode & MODE_X) > 0) {
//            let(xcur, MODE_X);
//        }
//        if ((mode & MODE_Y) > 0) {
//            let(ycur, MODE_Y);
//        }
//        if ((mode & MODE_SCALE) > 0) {
//            let(scale, MODE_SCALE);
//        }
//    }
//
//
//    public void start(final ImageControl.DrawThread dt) {
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                float denominator = 1;
//                float scale = 0;
//                for (int i = 1; i < 80; i++) {
//                    if (isCancle) {
//                        return;
//                    }
//                    denominator *= 14F / 15F;
//                    scale = (1 - denominator);
//                    float xcur = (xfrom - (xfrom - xto) * scale);
//                    float ycur = (yfrom - (yfrom - yto) * scale);
//                    float scur = (sfrom - (sfrom - sto) * scale);
//                    mlet(xcur, ycur, scur);
//                    dt.redraw();
//                    try {
//                        Thread.sleep(10);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                mlet(xto, yto, sto);
//                try {
//                    Thread.sleep(20);
//                } catch (InterruptedException e) {
//                    ;
//                }
//                dt.redraw();
//            }
//        }.start();
//
//    }
//}
//
//
//class MultiPoint {
//    public MultiPoint() {
//    }
//
//    public float x1 = 0, x2 = 0, y1 = 0, y2 = 0;
//    public int points = 0;
//
//    public void clear() {
//        x1 = x2 = y1 = y2 = points = 0;
//    }
//}
//
//class DrawItem {
//    private Bitmap bitmap;
//    private Drawable drawable;
//    public float translateX, translateY;
//
//    public float srcWidth, srcHeight;
//
//    public float width, height;
//
//    private float scale;
//
//    public void setScale(float scale) {
//        width = srcWidth * scale;
//        height = srcHeight * scale;
//        this.scale = scale;
//    }
//
//    public float getScale() {
//        return scale;
//    }
//
//    public void setBitmap(Bitmap bm) {
//        bitmap = bm;
//        srcWidth = bm.getWidth();
//        srcHeight = bm.getHeight();
//    }
//
//    public void setDrawable(Drawable db) {
//        drawable = db;
//        srcWidth = db.getIntrinsicWidth();
//        srcHeight = db.getIntrinsicHeight();
//    }
//
//    public void copy(DrawItem di) {
//        width = di.srcWidth;
//        height = di.srcHeight;
//    }
//
//    public Bitmap getBitmap() {
//        return bitmap;
//    }
//
//    public Drawable getDrawable() {
//        return drawable;
//    }
//}