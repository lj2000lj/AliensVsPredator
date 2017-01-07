package org.avp.event.client;

import java.util.ArrayList;
import java.util.Random;

import org.avp.event.client.Wavegraph.DataEntry.DisplayData;
import org.avp.event.client.Wavegraph.DataEntry.Interval;

import com.arisux.mdxlib.lib.client.render.Draw;
import com.arisux.mdxlib.lib.client.render.OpenGL;
import com.arisux.mdxlib.lib.game.Game;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class Wavegraph
{
    protected ArrayList<DataEntry> data;
    protected boolean              newdata;
    protected int                  rate;
    int                            refRate;
    int                            x;
    int                            y;
    int                            width;
    int                            height;
    int                            widthScale;
    int                            heightScale;
    int                            color;
    int                            backlightColor;
    int                            backgroundColor;
    int                            timespan;
    int                            flatlineHeight;
    int                            flatlineY;
    float                          lineWidth;

    public static class DataEntry
    {
        protected Wavegraph   graph;
        protected DisplayData displaydata;
        public long           time;
        private Interval      interval;

        public DataEntry(Wavegraph graph, Interval interval)
        {
            this(graph);
            this.interval = interval;
        }

        public DataEntry(Wavegraph graph)
        {
            this.graph = graph;
            this.time = System.currentTimeMillis();
            this.displaydata = new DisplayData(graph, this);
        }

        public int lengthMillis()
        {
            return (int) (interval.timeEnd - interval.timeStart);
        }

        public static class Segment
        {
            public long timeStart;
            public long timeEnd;

            public Segment(long timeStart, long timeEnd)
            {
                this.timeStart = timeStart;
                this.timeEnd = timeEnd;
            }
        }

        public static class Interval
        {
            public long  timeStart;
            public long  timeEnd;
            public float startValue;
            public float peakValue;
            public float endValue;

            public Interval(long timeStart, long timeEnd, float startValue, float peakValue, float endValue)
            {
                this.timeStart = timeStart;
                this.timeEnd = timeEnd;
                this.startValue = startValue;
                this.peakValue = peakValue;
                this.endValue = endValue;
            }
        }

        public static class DisplayData
        {
            protected Wavegraph e;
            protected DataEntry r;

            public int          recordTime;
            public int          recordElapseTime;
            public int          recordWidth;
            public int          recordHeight;
            public int          recordX;
            public int          recordY;

            public int          iTime;
            public int          iWidth;
            public int          iX;
            public int          iEndX;
            public int          iPeakX;
            public int          iY;
            public int          iPeakY;
            public int          iEndY;

            public DisplayData(Wavegraph e, DataEntry r)
            {
                this.e = e;
                this.r = r;
            }

            public void update(long curTime, int x, int y, int width, int height, int widthScale, int heightScale)
            {
                if (this.r == null)
                {
                    return;
                }

                recordTime = (int) (curTime - r.time) / 16;
                recordElapseTime = this.r.lengthMillis();
                recordWidth = recordElapseTime / widthScale;
                recordHeight = height / heightScale;
                recordX = x + (width - recordTime) + recordWidth;
                recordY = y;

                if (this.r.interval != null)
                {
                    iTime = (int) (((this.r.interval.timeEnd - this.r.interval.timeStart)));
                    iWidth = iTime / widthScale;
                    iX = recordX;
                    iEndX = iX + iWidth;
                    iPeakX = iEndX - (iWidth / 2);
                    iY = this.e.flatlineY - (int) Math.floor((height / 2) * this.r.interval.startValue);
                    iPeakY = this.e.flatlineY - (int) Math.floor((height / 2) * this.r.interval.peakValue);
                    iEndY = this.e.flatlineY - (int) Math.floor((height / 2) * this.r.interval.endValue);
                }
            }
        }

        public DisplayData displaydata()
        {
            return displaydata;
        }

        public Wavegraph getWavegraph()
        {
            return graph;
        }
    }

    public Wavegraph()
    {
        this.data = new ArrayList<DataEntry>();
        this.rate = 60;
        this.refRate = 80;
        this.width = 300;
        this.height = 80;
        this.widthScale = 16;
        this.heightScale = 16;
        this.color = 0xFF00CCFF;
        this.backgroundColor = 0x44000000;
        this.backlightColor = 0x44003344;
        this.lineWidth = 1F;
    }

    public void update(World world)
    {
        this.newdata = false;

        if (world.getWorldTime() % Math.floor((60D / this.rate) * 20) == 0 && !Game.minecraft().isGamePaused)
        {
            this.newdata = true;
            long start = System.currentTimeMillis();

            int pri = 100;

            pri = (int) Math.floor(pri / ((float) this.rate / this.refRate));

            Interval interval = new Interval(start, start + pri, 0F, new Random().nextFloat() / 2, -(new Random().nextFloat() / 2));

            this.data.add(new DataEntry(this, interval));
        }

        if (this.data.size() > 10)
        {
            for (DataEntry r : new ArrayList<DataEntry>(this.data))
            {
                if (this.data.indexOf(r) < this.data.size() - 45)
                {
                    this.data.remove(r);
                }
            }
        }
    }

    public void render(float partialTicks)
    {
        flatlineHeight = height / 2 / heightScale;
        flatlineY = (y + height - flatlineHeight) - (height / 2);

        OpenGL.enableBlend();
        Draw.drawRect(x, y, width, height, newdata ? backlightColor : backgroundColor);
        OpenGL.disableTexture2d();
        this.drawRecords(partialTicks);
        OpenGL.enableTexture2d();
        OpenGL.disableBlend();
    }

    public void drawRecords(float partialTicks)
    {
        float depth = 10F;
        DataEntry previousRecord = null;

        for (DataEntry r : new ArrayList<DataEntry>(this.data))
        {
            DisplayData data = r.displaydata();
            data.update(System.currentTimeMillis(), x, y, width, height, widthScale, heightScale);

            if (data.iEndX >= x)
            {
                if (previousRecord != null)
                {
                    if (data.iX <= x + width)
                    {
                        Draw.line(data.iX, data.iY, previousRecord.displaydata().iEndX, previousRecord.displaydata().iEndY, depth, lineWidth, color);
                    }
                    else
                    {
                        Draw.line(x + width, flatlineY, previousRecord.displaydata().iEndX, previousRecord.displaydata().iEndY, depth, lineWidth, color);
                    }
                }
                else if (data.iEndX >= x)
                {
                    Draw.line(data.iX, data.iY, x, flatlineY, depth, lineWidth, color);
                }

                if (!(this.data.size() > this.data.indexOf(r) + 1))
                {
                    if (data.iEndX <= x + width)
                    {
                        Draw.line(data.iEndX, data.iEndY, x + width, flatlineY, depth, lineWidth, color);
                    }
                }

                if (data.iX >= x && data.iX <= (x + width))
                    Draw.line(data.iX, data.iY, data.iPeakX, data.iPeakY, depth, lineWidth, color);

                if (data.iPeakX >= x && data.iPeakX <= (x + width))
                    Draw.line(data.iPeakX, data.iPeakY, data.iEndX, data.iEndY, depth, lineWidth, color);
            }
            else
            {
                this.data.remove(r);
            }

            previousRecord = r;
        }
    }

    public ArrayList<DataEntry> getData()
    {
        return data;
    }

    public int getRate()
    {
        return rate;
    }

    public Wavegraph setRate(int rate)
    {
        this.rate = rate;
        return this;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public Wavegraph setColor(int color)
    {
        this.color = color;
        return this;
    }

    public Wavegraph setX(int x)
    {
        this.x = x;
        return this;
    }

    public Wavegraph setY(int y)
    {
        this.y = y;
        return this;
    }

    public Wavegraph setWidth(int width)
    {
        this.width = width;
        return this;
    }

    public Wavegraph setHeight(int height)
    {
        this.height = height;
        return this;
    }

    public Wavegraph setScale(int widthScale, int heightScale)
    {
        this.widthScale = widthScale;
        this.heightScale = heightScale;
        return this;
    }

    public Wavegraph setBackgroundColor(int backgroundColor)
    {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public Wavegraph setBacklightColor(int backlightColor)
    {
        this.backlightColor = backlightColor;
        return this;
    }

    public Wavegraph setLineWidth(float lineWidth)
    {
        this.lineWidth = lineWidth;
        return this;
    }
}
