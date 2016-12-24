    private void testNinePatch() {
        // String pngName = "addto_bookmarks_bg_complied.9.png";
        String pngName = "barcode_actionbar_pressed_background_complied.9.png";
        try {
            InputStream is = getResources().getAssets().open(pngName);
            // Bitmap bitmap = BitmapFactory.decodeStream(is);
            // byte[] chunkData1 = bitmap.getNinePatchChunk();
            byte[] chunkData2 = loadNinePatchChunk(is);
            NinePatchChunk mChunk = chunkData2 == null ? null : NinePatchChunk
                    .deserialize(chunkData2);
            if (mChunk == null) {
                throw new RuntimeException("invalid nine-patch image: " + pngName);
            }
            printArray(mChunk.mDivX);
            printArray(mChunk.mDivY);
            System.out.println(mChunk.mPaddings.toShortString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * PNG Chunk struct
     * <a href="http://dev.exiv2.org/projects/exiv2/wiki/The_Metadata_in_PNG_files">The Metadata in PNG files</a>
     * 
     *   +--------+---------+
     *   | Length | 4 bytes |
     *   +--------+---------+
     *   | Chunk  | 4 bytes |
     *   |  type  |         |
     *   +--------+---------+
     *   | Chunk  | Length  |
     *   |  data  |  bytes  |
     *   +--------+---------+
     *   | CRC    | 4 bytes |
     *   +--------+---------+
     *   
     * @param pngName
     * @return chunk
     * @throws IOException
     */
    private byte[] loadNinePatchChunk(InputStream is) throws IOException {
        IntReader reader = new IntReader(is, true);
        // check PNG signature
        // A PNG always starts with an 8-byte signature: 137 80 78 71 13 10 26 10 (decimal values).
        if (reader.readInt() != 0x89504e47 || reader.readInt() != 0x0D0A1A0A) {
            return null;
        }

        while (true) {
            int length = reader.readInt();
            int type = reader.readInt();
            // check for nine patch chunk type (npTc)
            if (type != 0x6E705463) {
                reader.skip(length + 4/*crc*/);
                continue;
            }
            return reader.readByteArray(length);
        }
    }
