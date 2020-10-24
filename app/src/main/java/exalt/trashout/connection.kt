package exalt.trashout

import android.net.Uri
import java.io.*
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

private fun buildStringFromInputStream(inputStream: InputStream): String {
    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
    var line: String?

    val responseStringBuilder = StringBuilder()
    while (bufferedReader.readLine().also { line = it } != null) {
        responseStringBuilder.append(line)
    }
    inputStream.close()
    return responseStringBuilder.toString()
}

fun getRequestResponse(url2: String, uri: Uri): String {
    var response = ""
    try {
        val conn: HttpURLConnection
        val dos: DataOutputStream
        val lineEnd = "\r\n";
        val twoHyphens = "--";
        val boundary = "*****";
        var bytesRead: Int
        var bytesAvailable: Int
        var bufferSize: Int
        val maxBufferSize = 1024 * 1024
        val sourceFile = File(uri.path!!)

        if (sourceFile.isFile) {

            try {
                val fileInputStream = FileInputStream(sourceFile)
                val url = URL(url2);

                conn = url.openConnection() as HttpURLConnection
                conn.doInput = true
                conn.doOutput = true
                conn.useCaches = false
                conn.requestMethod = "POST";
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty(
                    "ENCTYPE",
                    "multipart/form-data"
                );
                conn.setRequestProperty(
                    "Content-Type",
                    "multipart/form-data;boundary=$boundary"
                );
                conn.setRequestProperty("bill", uri.path);

                dos = DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes(
                    "Content-Disposition: form-data; name=\"bill\";filename=\""
                            + "image" + "\"" + lineEnd
                )

                dos.writeBytes(lineEnd);

                bytesAvailable = fileInputStream.available()

                bufferSize = Math.min(bytesAvailable, maxBufferSize)

                var buffer = ByteArray(bufferSize)
                bytesRead = fileInputStream.read(buffer, 0, bufferSize)
                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math
                        .min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(
                        buffer, 0,
                        bufferSize
                    )
                }
                dos.writeBytes(lineEnd);
                dos.writeBytes(
                    twoHyphens + boundary + twoHyphens
                            + lineEnd
                )
                conn.inputStream.let { stream ->
                    response = buildStringFromInputStream(stream)
                }
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (e: Exception) {

                e.printStackTrace();

            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return response
}
