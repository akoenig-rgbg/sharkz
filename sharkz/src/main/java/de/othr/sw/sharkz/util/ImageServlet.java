package de.othr.sw.sharkz.util;

import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.service.InsertionService;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;

@WebServlet("/images")
public class ImageServlet extends HttpServlet {

    @Inject
    InsertionService insertionService;
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int imageId = 0;
        
        Long insertionId = Long.parseLong(request.getParameter("insertion_id"));
        
        try {
            imageId = Integer.parseInt(request.getParameter("image_id"));
        } catch (NumberFormatException e) {
            // image_id parameter can be left out. Default is '0' as there is
            // at least one image per insertion.
        }
        
        Insertion ins = insertionService.findInsertion(insertionId);
        
        List<byte[]> images = new ArrayList<>(ins.getImages());
        
        byte[] bytes = images.get(imageId);
        
        String mimeType;
        
        // JMimeMagic library guesses mimetype of picture
        try {
            MagicMatch match = Magic.getMagicMatch(bytes);
            mimeType = match.getMimeType();
        } catch (MagicException | MagicMatchNotFoundException
                | MagicParseException ex) {
            mimeType = "image/png";
        }
        
        response.setContentType(mimeType);
        OutputStream out = response.getOutputStream();
        out.write(bytes);
    }
}
