package org.avp.event.client;

import java.util.ArrayList;

import org.avp.event.client.Electrocardiogram.DataEntryEKG.DisplayDataEKG;
import org.avp.event.client.Wavegraph.DataEntry.Interval;
import org.avp.event.client.Wavegraph.DataEntry.Segment;

import com.arisux.mdxlib.lib.client.render.Draw;
import com.arisux.mdxlib.lib.client.render.OpenGL;
import com.arisux.mdxlib.lib.game.Game;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class Electrocardiogram extends Wavegraph
{
    private ArrayList<DataEntryEKG> data;

    public static class DataEntryEKG extends DataEntry
    {
        public Interval       prInterval;
        public Segment        prSegment;
        public Interval       qrsInterval;
        public Segment        stSegment;
        public Interval       tInterval;

        public DataEntryEKG(Wavegraph graph, Interval prInterval, Segment prSegment, Interval qrsInterval, Segment stSegment, Interval tInterval)
        {
            super(graph);
            this.displaydata = new DisplayDataEKG(graph, this);
            this.prInterval = prInterval;
            this.prSegment = prSegment;
            this.qrsInterval = qrsInterval;
            this.stSegment = stSegment;
            this.tInterval = tInterval;
        }

        @Override
        public int lengthMillis()
        {
            return (int) ((prInterval.timeEnd - prInterval.timeStart) + (prSegment.timeEnd - prSegment.timeStart) + (qrsInterval.timeEnd - qrsInterval.timeStart) + (stSegment.timeEnd - stSegment.timeStart) + (tInterval.timeEnd - tInterval.timeStart));
        }

        public static class DisplayDataEKG extends DisplayData
        {
            public int             prTime;
            public int             prWidth;
            public int             prX;
            public int             preX;
            public int             prpX;
            public int             prY;
            public int             prpY;
            public int             preY;

            public int             prsTime;
            public int             prsWidth;
            public int             prsX;
            public int             prseX;

            public int             qrsTime;
            public int             qrsWidth;
            public int             qrsX;
            public int             qrseX;
            public int             qrspX;
            public int             qrsY;
            public int             qrspY;
            public int             qrseY;

            public int             stsTime;
            public int             stsWidth;
            public int             stsX;
            public int             stseX;

            public int             tTime;
            public int             tWidth;
            public int             tX;
            public int             teX;
            public int             tpX;
            public int             tY;
            public int             tpY;
            public int             teY;

            public DisplayDataEKG(Wavegraph e, DataEntry r)
            {
                super(e, r);
            }

            @Override
            public void update(long curTime, int x, int y, int width, int height, int widthScale, int heightScale)
            {
                super.update(curTime, x, y, width, height, widthScale, heightScale);
                
                DataEntryEKG entry = (DataEntryEKG) this.r;
                
                prTime = (int) (((entry.prInterval.timeEnd - entry.prInterval.timeStart)));
                prWidth = prTime / widthScale;
                prX = recordX;
                preX = prX + prWidth;
                prpX = preX - (prWidth / 2);
                prY = this.e.flatlineY - (int) Math.floor((height / 2) * entry.prInterval.startValue);
                prpY = this.e.flatlineY - (int) Math.floor((height / 2) * entry.prInterval.peakValue);
                preY = this.e.flatlineY - (int) Math.floor((height / 2) * entry.prInterval.endValue);

                prsTime = (int) (entry.prSegment.timeEnd - entry.prSegment.timeStart);
                prsWidth = prsTime / widthScale;
                prsX = preX;
                prseX = prsX + prsWidth;

                qrsTime = (int) (entry.qrsInterval.timeEnd - entry.qrsInterval.timeStart);
                qrsWidth = qrsTime / widthScale;
                qrsX = prseX;
                qrseX = qrsX + qrsWidth;
                qrspX = qrseX - (qrsWidth / 2);
                qrsY = this.e.flatlineY - (int) Math.floor((height / 2) * entry.qrsInterval.startValue);
                qrspY = this.e.flatlineY - (int) Math.floor((height / 2) * entry.qrsInterval.peakValue);
                qrseY = this.e.flatlineY - (int) Math.floor((height / 2) * entry.qrsInterval.endValue);

                stsTime = (int) (entry.stSegment.timeEnd - entry.stSegment.timeStart);
                stsWidth = stsTime / widthScale;
                stsX = qrseX;
                stseX = stsX + stsWidth;

                tTime = (int) (entry.tInterval.timeEnd - entry.tInterval.timeStart);
                tWidth = tTime / widthScale;
                tX = stseX;
                teX = tX + tWidth;
                tpX = teX - (tWidth / 2);
                tY = this.e.flatlineY - (int) Math.floor((height / 2) * entry.tInterval.startValue);
                tpY = this.e.flatlineY - (int) Math.floor((height / 2) * entry.tInterval.peakValue);
                teY = this.e.flatlineY - (int) Math.floor((height / 2) * entry.tInterval.endValue);
            }
        }

        @Override
        public DisplayDataEKG displaydata()
        {
            return (DisplayDataEKG) displaydata;
        }

        @Override
        public Wavegraph getWavegraph()
        {
            return graph;
        }
    }

    public Electrocardiogram()
    {
        this.data = new ArrayList<DataEntryEKG>();
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

    @Override
    public void update(World world)
    {
        this.newdata = false;

        if (world.getWorldTime() % Math.floor((60D / this.rate) * 20) == 0 && !Game.minecraft().isGamePaused)
        {
            this.newdata = true;
            long start = System.currentTimeMillis();

            int pri = 100;
            int prs = 50;
            int qrsi = 75;
            int sts = 100;
            int ti = 75;

            pri = (int) Math.floor(pri / ((float) this.rate / this.refRate));
            prs = (int) Math.floor(prs / ((float) this.rate / this.refRate));
            qrsi = (int) Math.floor(qrsi / ((float) this.rate / this.refRate));
            sts = (int) Math.floor(sts / ((float) this.rate / this.refRate));
            ti = (int) Math.floor(ti / ((float) this.rate / this.refRate));

            Interval prInterval = new Interval(start, start + pri, 0F, 0.25F, 0F);
            Segment prSegment = new Segment(prInterval.timeEnd, prInterval.timeEnd + prs);
            Interval qrsInterval = new Interval(prSegment.timeEnd, prSegment.timeEnd + qrsi, 0F, 0.8F, -0.25F);
            Segment stSegment = new Segment(qrsInterval.timeEnd, qrsInterval.timeEnd + sts);
            Interval tInterval = new Interval(stSegment.timeEnd, stSegment.timeEnd + ti, 0F, 0.15F, 0F);

            this.data.add(new DataEntryEKG(this, prInterval, prSegment, qrsInterval, stSegment, tInterval));
        }

        if (this.data.size() > 10)
        {
            for (DataEntry r : new ArrayList<DataEntry>(this.data))
            {
                if (this.data.indexOf(r) < this.data.size() - 30)
                {
                    this.data.remove(r);
                }
            }
        }
    }

    @Override
    public void render(float partialTicks)
    {
        flatlineHeight = height / 2 / heightScale;
        flatlineY = (y + height - flatlineHeight) - (height / 2);

        OpenGL.enableBlend();
        Draw.drawRect(x, y, width, height, newdata ? backlightColor : backgroundColor);
        this.drawBPMString(partialTicks);
        OpenGL.disableTexture2d();
        this.drawRecords(partialTicks);
        OpenGL.enableTexture2d();
        OpenGL.disableBlend();
    }

    @Override
    public void drawRecords(float partialTicks)
    {
        float depth = 10F;
        DataEntryEKG previousRecord = null;

        for (DataEntryEKG r : new ArrayList<DataEntryEKG>(this.data))
        {
            DisplayDataEKG data = r.displaydata();
            data.update(System.currentTimeMillis(), x, y, width, height, widthScale, heightScale);

            if (data.teX >= x)
            {
                if (previousRecord != null)
                {
                    if (data.prX <= x + width)
                    {
                        Draw.line(data.prX, data.prY, previousRecord.displaydata().teX, previousRecord.displaydata().teY, depth, lineWidth, color);
                    }
                    else
                    {
                        Draw.line(x + width, flatlineY, previousRecord.displaydata().teX, previousRecord.displaydata().teY, depth, lineWidth, color);
                    }
                }
                else if (data.preX >= x)
                {
                    Draw.line(data.prX, data.prY, x, flatlineY, depth, lineWidth, color);
                }

                if (!(this.data.size() > this.data.indexOf(r) + 1))
                {
                    if (data.teX <= x + width)
                    {
                        Draw.line(data.teX, data.teY, x + width, flatlineY, depth, lineWidth, color);
                    }
                }

                if (data.prX >= x && data.prX <= (x + width))
                    Draw.line(data.prX, data.prY, data.prpX, data.prpY, depth, lineWidth, color);

                if (data.prpX >= x && data.prpX <= (x + width))
                    Draw.line(data.prpX, data.prpY, data.preX, data.preY, depth, lineWidth, color);

                if (data.preX >= x && data.preX <= (x + width))
                    Draw.line(data.preX, data.preY, data.qrsX, data.qrsY, depth, lineWidth, color);

                if (data.qrsX >= x && data.qrsX <= (x + width))
                    Draw.line(data.qrsX, data.qrsY, data.qrspX, data.qrspY, depth, lineWidth, color);

                if (data.qrspX >= x && data.qrspX <= (x + width))
                    Draw.line(data.qrspX, data.qrspY, data.qrseX, data.qrseY, depth, lineWidth, color);

                if (data.qrseX >= x && data.qrseX <= (x + width))
                    Draw.line(data.qrseX, data.qrseY, data.tX, data.tY, depth, lineWidth, color);

                if (data.tX >= x && data.tX <= (x + width))
                    Draw.line(data.tX, data.tY, data.tpX, data.tpY, depth, lineWidth, color);

                if (data.tpX >= x && data.tpX <= (x + width))
                    Draw.line(data.tpX, data.tpY, data.teX, data.teY, depth, lineWidth, color);
            }
            else
            {
                this.data.remove(r);
            }

            previousRecord = r;
        }
    }

    public void drawBPMString(float partialTicks)
    {
        Draw.drawString(this.getBPMString(this.rate), x + 4, y + 4, color, false);
    }

    protected String getBPMString(int bpm)
    {
        return String.format("%sBPM", bpm);
    }

    @Override
    public int getRate()
    {
        return rate;
    }

    @Override
    public Electrocardiogram setRate(int bpm)
    {
        this.rate = bpm;
        return this;
    }
}
