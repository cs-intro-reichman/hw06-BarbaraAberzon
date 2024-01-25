// Source code is decompiled from a .class file using FernFlower decompiler.
import java.awt.Color;

public class Editor4 {
   public Editor4() {
   }

   public static void main(String[] var0) {
      String var1 = var0[0];
      int var2 = Integer.parseInt(var0[1]);
      Color[][] var3 = Runigram.read(var1);
      Color[][] var4 = Runigram.grayScaled(var3);
    
      Runigram.setCanvas(var3);
      Runigram.morph(var3, var4, var2);
   }
}
