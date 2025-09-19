import AdminHeader from "./AdminHeader";
import UserHeader from "./UserHeader";
import DefaultHeader from "./DefaultHeader";

export default function Header({ user, setUser }) {
  const logout = () => {
    localStorage.removeItem("user");
    setUser(null);
    window.location.href = "/defaultdashboard"; // redirect to default dashboard
  };

  if (user?.role === "LIBRARIAN") return <AdminHeader user={user} logout={logout} />;
  if (user?.role === "CUSTOMER") return <UserHeader user={user} logout={logout} />;

  return <DefaultHeader />; // no logged-in user
}

