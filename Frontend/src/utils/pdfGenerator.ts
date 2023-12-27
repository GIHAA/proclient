import { toast } from 'react-toastify'; 
import jsPDF from 'jspdf';
import 'jspdf-autotable';

export const PdfGenerator = (data : any) => {
  toast.info("Generating Report");
  const name = "Customer Report";
  const pdf_title = "Customer Report";
  const pdf_address = "info@proclient.com";
  const pdf_phone = "+94 11 234 5678";
  const pdf_email = "Address: No 221/B, Peradeniya Road, Kandy";

  const doc = new jsPDF("landscape", "px", "a4", false);
  const today = new Date();
  const date = `${today.getFullYear()}-${today.getMonth() + 1}-${today.getDate()}`;

  const title = `${pdf_title}`;
  doc.setFont("helvetica");
  doc.setTextColor("#000000");

  doc.setFontSize(24);
  doc.text(title, 30, 30);
  doc.setFontSize(12);
  doc.setTextColor("#999999");
  doc.text(`Generated on ${date}`, 30, 40);
  // doc.addImage(logo, "JPG", 20, 60, 70, 40);
  doc.setFontSize(16);
  doc.setFont("helvetica", "bold");
  doc.setTextColor("#000000");
  doc.text("ProClient Manager", 30, 70);
  doc.setFont("helvetica", "normal");
  doc.setFontSize(10);
  doc.setTextColor("#999999");
  doc.text(`Tel: ${pdf_phone}`, 30, 80);
  doc.text(`Email: ${pdf_email}`, 30, 90);
  doc.text(`Address: ${pdf_address}`, 30, 100);
  doc.line(20, 110, 600, 110);

  // Add table with data
  doc.setTextColor("#999999");
  doc.setFontSize(12);
  doc.setTextColor("#000000");

  doc.autoTable({
    startY: 130,
    head: [["Id", "Name", "Email", "Phone Number", "Gender"]],
    body: data.map((request: any) => [
      request.id,
      request.firstName,
      request.email,
      request.phoneNumber,
      request.gender,
    ]),
    theme: "grid",
  });

  doc.save(`${name}.pdf`);
};