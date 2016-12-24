NinePatchBuilder builder=new NinePatchBuilder(getResources(), bitmap);
NinePatchDrawable drawable=builder.addXCenteredRegion(2).addYCenteredRegion(2).build();

//or add multiple patches

NinePatchBuilder builder=new NinePatchBuilder(getResources(), bitmap);
builder.addXRegion(30,2).addXRegion(50,1).addYRegion(20,4);
byte[] chunk=builder.buildChunk();
NinePatch ninepatch=builder.buildNinePatch();
NinePatchDrawable drawable=builder.build();

//Here if you don't want ninepatch and only want chunk use
NinePatchBuilder builder=new NinePatchBuilder(width, height);
byte[] chunk=builder.addXCenteredRegion(1).addYCenteredRegion(1).buildChunk();
