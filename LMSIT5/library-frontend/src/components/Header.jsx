import { useLocation } from "react-router-dom";
import AdminHeader from "./AdminHeader";
import UserHeader from "./UserHeader";

export default function Header() {
  const location = useLocation();
  const isAdminPage = location.pathname.startsWith("/admin");

  // temporary fake auth
  const user = null;
  const logout = () => console.log("Logout clicked");

  return isAdminPage ? (
    <AdminHeader logout={logout} />
  ) : (
    <UserHeader user={user} logout={logout} />
  );
}